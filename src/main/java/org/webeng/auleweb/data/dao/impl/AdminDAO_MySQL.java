/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.dao.impl;

import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.webeng.auleweb.data.dao.AdminDAO;
import org.webeng.auleweb.data.model.Admin;
import org.webeng.auleweb.data.model.impl.proxy.AdminProxy;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.OptimisticLockException;
import org.webeng.auleweb.framework.security.SecurityHelpers;

/**
 *
 * @author enric
 */
public class AdminDAO_MySQL extends DAO implements AdminDAO {

    private PreparedStatement sAdminByID, sAdminByName;
    private PreparedStatement sAdmins;
    private PreparedStatement iAdmin, uAdmin, dAdmin;

    public AdminDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sAdminByID = connection.prepareStatement("SELECT * FROM admin WHERE ID=?");
            sAdminByName = connection.prepareStatement("Select * FROM admin WHERE admin.username = ?");

            sAdmins = connection.prepareStatement("SELECT id FROM admin");

            iAdmin = connection.prepareStatement("INSERT INTO admin(`username`,`password`) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            uAdmin = connection.prepareStatement("UPDATE admin set username=?, password=?, version=? WHERE id=? and version=?");
            dAdmin = connection.prepareStatement("DELETE FROM admin WHERE id=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing admin data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sAdminByID.close();
            sAdminByName.close();

            sAdmins.close();

            iAdmin.close();
            uAdmin.close();
            dAdmin.close();
        } catch (SQLException ex) {
            throw new DataException("Error destroying admin prepared statements", ex);
        }
        super.destroy();
    }

    @Override
    public Admin createAdmin() {
        return new AdminProxy(getDataLayer());
    }

    public Admin createAdmin(ResultSet rs) throws DataException {
        AdminProxy a = (AdminProxy) createAdmin();
        try {
            a.setKey(rs.getInt("id"));
            a.setUsername(rs.getString("username"));
            a.setPassword(rs.getString("password"));
            a.setToken(rs.getString("token"));
            a.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create admin object form ResultSet", ex);
        }
        return a;
    }

    @Override
    public Admin getAdmin(int admin_key) throws DataException {
        Admin a = null;
        if (dataLayer.getCache().has(Admin.class, admin_key)) {
            a = dataLayer.getCache().get(Admin.class, admin_key);
        } else {
            try {
                sAdminByID.setInt(1, admin_key);
                try (ResultSet rs = sAdminByID.executeQuery()) {
                    if (rs.next()) {
                        a = createAdmin(rs);
                        dataLayer.getCache().add(Admin.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load admin by ID", ex);

            }
        }
        return a;
    }

    @Override
    public Admin getAdminByName(String username) throws DataException {
        Admin a = null;
        try {
            sAdminByName.setString(1, username);
            try (ResultSet rs = sAdminByName.executeQuery()) {
                if (rs.next()) {
                    a = createAdmin(rs);
                    dataLayer.getCache().add(Admin.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load admin by username", ex);
        }
        return a;
    }

    @Override
    public List<Admin> getAdmins() throws DataException {
        List<Admin> result = new ArrayList();
        try (ResultSet rs = sAdmins.executeQuery()) {
            while (rs.next()) {
                result.add((Admin) getAdmin(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load admins", ex);
        }
        return result;
    }

    @Override
    public void storeAdmin(Admin admin) throws DataException {
        try {
            if (admin.getKey() != null && admin.getKey() > 0) {
                if (admin instanceof DataItemProxy && !((DataItemProxy) admin).isModified()) {
                    return;
                }
                // UPDATE
                uAdmin.setString(1, admin.getUserame());
                uAdmin.setString(2, admin.getPassword());
                uAdmin.setString(3, admin.getToken());

                long current_version = admin.getVersion();
                long next_version = current_version + 1;

                uAdmin.setLong(4, next_version);
                // WHERE ID = ? AND VERSION = ?
                uAdmin.setInt(5, admin.getKey());
                uAdmin.setLong(6, current_version);

                if (uAdmin.executeUpdate() == 0) {
                    throw new OptimisticLockException(admin);
                } else {
                    admin.setVersion(next_version);
                }
            } else { //INSERT
                iAdmin.setString(1, admin.getUserame());
                iAdmin.setString(2, admin.getPassword());
                iAdmin.setString(3, admin.getToken());

                if (iAdmin.executeUpdate() == 1) {
                    try (ResultSet keys = iAdmin.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            admin.setKey(key);
                            dataLayer.getCache().add(Admin.class, admin);
                        }
                    }
                }
            }
            // RESET dell'attributo modified
            if (admin instanceof DataItemProxy) {
                ((DataItemProxy) admin).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store admin", ex);
        }
    }

}
