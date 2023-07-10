package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.MatterDao;
import model.entities.Matter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatterDaoJDBC implements MatterDao {
    private Connection conn;

    public MatterDaoJDBC(Connection conn) {
            this.conn = conn;
        }

        @Override
        public void insert(Matter obj) {
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement("insert into matter " +
                                "(Name) " +
                                "values (?) ",
                        Statement.RETURN_GENERATED_KEYS);

                st.setString(1, obj.getName());

                int rowsAffected = st.executeUpdate();

                if (rowsAffected > 0){
                    ResultSet rs = st.getGeneratedKeys();
                    if (rs.next()){
                        int id = rs.getInt(1);
                        obj.setId(id);
                    }
                    DB.closeResultSet(rs);
                } else {
                    throw new DbException("Error! No rows affected!");
                }

            } catch (SQLException e){
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
            }
        }

        @Override
        public void update(Matter obj) {

            PreparedStatement st = null;
            try {
                st = conn.prepareStatement("update department " +
                        "set Name = ? " +
                        "where Id = ?");

                st.setString(1, obj.getName());
                st.setInt(2, obj.getId());

                int rowsAffected = st.executeUpdate();

                if (rowsAffected == 0){
                    throw new DbException("Error! No rows affected!");
                }

            } catch (SQLException e){
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
            }
        }

        @Override
        public void deleteById(Integer id) {
            PreparedStatement st = null;
            try{
                st = conn.prepareStatement("delete from matter where Id = ?");

                st.setInt(1, id);

                int rowsAffected = st.executeUpdate();

                if (rowsAffected == 0){
                    throw new DbException("Materia inexistente!");
                }

            } catch (SQLException e){
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
            }
        }

        @Override
        public Matter findById(Integer id) {

            PreparedStatement st = null;
            ResultSet rs = null;
            try{
                st = conn.prepareStatement("" +
                        "select * from department " +
                        "where Id = ?");

                st.setInt(1, id);
                rs = st.executeQuery();
                if (rs.next()){
                    Matter dep = instantiateMatter(rs);
                    return dep;

                }
                return null;
            } catch (SQLException e){
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }

        }

        private Matter instantiateMatter(ResultSet rs) throws SQLException {
            Matter dep = new Matter();
            dep.setId(rs.getInt("Id"));
            dep.setName(rs.getString("Name"));
            return dep;
        }

        @Override
        public List<Matter> findAll() {

            PreparedStatement st = null;
            ResultSet rs = null;
            try{
                st = conn.prepareStatement("" +
                        "select * from department "+
                        "order by Name");

                rs = st.executeQuery();

                List<Matter> list = new ArrayList<>();
                Map<Integer, Matter> map = new HashMap<>();

                while (rs.next()){

                    Matter dep = map.get(rs.getInt("Id"));

                    if (dep == null){
                        dep = instantiateMatter(rs);
                        map.put(rs.getInt("Id"), dep);
                    }

                    list.add(dep);

                }
                return list;

            } catch (SQLException e){
                throw new DbException(e.getMessage());

            } finally {
                DB.closeStatement(st);
                DB.closeResultSet(rs);
            }


        }
    }
