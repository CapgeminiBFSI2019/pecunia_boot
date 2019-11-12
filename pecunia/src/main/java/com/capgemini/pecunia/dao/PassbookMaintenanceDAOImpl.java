package com.capgemini.pecunia.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Transaction;
import com.capgemini.pecunia.repository.PassbookRepository;



public class PassbookMaintenanceDAOImpl {

	@Autowired
	PassbookRepository passbook;
	
	public List<Transaction> updatePassbook(String accountId) throws PassbookException, PecuniaException {
		List<Transaction> transList = new ArrayList<>();
		
		try {
//			Session session = HibernateUtil.getSessionFactory().openSession();
//			String hql = "from TransactionEntity where accountId= :accountId AND date BETWEEN (SELECT lastUpdated from AccountEntity where accountId= :accountId) and :currentDate";
//			Query<TransactionEntity> query = session.createQuery(hql);
			transList= passbook.findById(accountId, LocalDateTime.now().plusMinutes(330));
//			query.setParameter("accountId", accountId);
//			query.setParameter("currentDate",java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(330)));
//			List<TransactionEntity> results = (List<TransactionEntity>)query.list();
//            transList = passbookDetails(results);            
     
		}
		catch(Exception e) {
            throw new PassbookException(ErrorConstants.UPDATE_PASSBOOK_ERROR);
        }
	 return transList;
	}
	

	
}
