package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iCommentDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;
import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO implements iCommentDAO {
    private static CommentDAO instance;

    private CommentDAO() {
    }

    public static CommentDAO getInstance() {
        if (instance == null) {
            instance = new CommentDAO();
        }
        return instance;
    }

    @Override
    public Comment add(Comment comment) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(comment);
            transaction.commit();
            return comment;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    @Override
    public boolean delete(int commentId) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            Comment comment = manager.find(Comment.class, commentId);
            manager.remove(comment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    @Override
    public List<Comment> searchAllByIdList(int idList) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        List<Comment> comments = new ArrayList<>();

        try {
            TypedQuery<Comment> query = manager.createQuery("SELECT c FROM Comment c WHERE c.reproductionListId = :idList", Comment.class);
            query.setParameter("idList", idList);
            comments = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (manager != null) {
                manager.close();
            }
        }

        if (comments.isEmpty()) {
            return null;
        }
        return comments;
    }

    public Comment searchComment(int idComment) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        try {
            return manager.find(Comment.class, idComment);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }
}