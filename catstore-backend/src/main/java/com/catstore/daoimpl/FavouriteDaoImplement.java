package com.catstore.daoimpl;

import com.catstore.dao.FavouriteDao;
import com.catstore.repository.FavouriteRepository;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class FavouriteDaoImplement implements FavouriteDao {

    FavouriteRepository favouriteRepository;

    @Autowired
    void setFavouriteRepository(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    public boolean addFavouriteBook(Integer bookId) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            if (favouriteRepository.getFavouriteByUserIdAndBookId(userId, bookId) == null) {
                favouriteRepository.addFavouriteBook(userId, bookId);
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteFavouriteBook(Integer bookId) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null)
            favouriteRepository.deleteFavouriteBookByUserIdAndBookId(userId, bookId);
    }

    @Override
    public List<Map<String, String>> getFavouriteBooks() {
        Integer userId = SessionUtil.getUserId();
        if (userId != null)
            return favouriteRepository.getFavouriteBooksByUserId(userId);
        return null;
    }
}
