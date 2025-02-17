package vn.iostar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import vn.iostar.configs.JPAConfig;
import vn.iostar.dao.IVideoDao;
import vn.iostar.entity.Video;

public class VideoDao implements IVideoDao{
	@Override
	public void insert(Video video) {
		EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(video);  // Thêm mới một video
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
	}

	@Override
	public void update(Video video) {
		// TODO Auto-generated method stub
		 EntityManager enma = JPAConfig.getEntityManager();
	        EntityTransaction trans = enma.getTransaction();
	        try {
	            trans.begin();
	            enma.merge(video);  // Cập nhật một video
	            trans.commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	            trans.rollback();
	            throw e;
	        } finally {
	            enma.close();
	        }
		
	}

	@Override
	public void delete(String videoId) throws Exception {
		// TODO Auto-generated method stub
		EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Video video = enma.find(Video.class, videoId);  // Tìm kiếm video theo ID

            if (video != null) {
                enma.remove(video);  // Xóa video
            } else {
                throw new Exception("Không tìm thấy video");
            }

            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
	}

	@Override
	public Video findById(String videoId) {
		// TODO Auto-generated method stub
		 EntityManager enma = JPAConfig.getEntityManager();
	     return enma.find(Video.class, videoId);  // Tìm video theo ID
	}

	@Override
	public List<Video> findAll() {
		// TODO Auto-generated method stub
		 EntityManager enma = JPAConfig.getEntityManager();
	     TypedQuery<Video> query = enma.createNamedQuery("Video.findAll", Video.class);  // Truy vấn tất cả video
	     return query.getResultList();
	}

	@Override
	public List<Video> findByTitle(String title) {
		// TODO Auto-generated method stub
		EntityManager enma = JPAConfig.getEntityManager();
        String jpql = "SELECT v FROM Video v WHERE v.title like :title";
        TypedQuery<Video> query = enma.createQuery(jpql, Video.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
	}

	@Override
	public List<Video> findAll(int page, int pageSize) {
		EntityManager enma = JPAConfig.getEntityManager();
        TypedQuery<Video> query = enma.createNamedQuery("Video.findAll", Video.class);
        query.setFirstResult(page * pageSize);  // Thiết lập vị trí bắt đầu
        query.setMaxResults(pageSize);  // Giới hạn số lượng kết quả
        return query.getResultList();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		EntityManager enma = JPAConfig.getEntityManager();
        String jpql = "SELECT count(v) FROM Video v";
        Query query = enma.createQuery(jpql);
        return ((Long) query.getSingleResult()).intValue();
	}

}
