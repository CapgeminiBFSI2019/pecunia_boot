package com.capgemini.pecunia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.model.Loan;
import com.capgemini.pecunia.model.LoanDisbursal;
import com.capgemini.pecunia.repository.AccountRepository;
import com.capgemini.pecunia.repository.LoanDisbursalRepository;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.dao.LoanDisbursalDAO;
import com.capgemini.pecunia.util.Constants;


@Component
public class LoanDisbursalDAOImpl implements LoanDisbursalDAO {
	
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	LoanDisbursalRepository loanDisbursalRepository;

	private int loanId;
	private String accountId;
	private Double amount;
	private String type;
	private int tenure;
	private double roi;
	private String status;
	private Double emi;
	private int creditScore;

	private int loanIdOfAccepted;
	private String accountIdOfAccepted;
	private Double amountOfAccepted;
	private String typeOfAccepted;
	private int tenureOfAccepted;
	private double roiOfAccepted;
	private String statusOfAccepted;
	private Double emiOfAccepted;
	private int creditScoreOfAccepted;

	private int loanIdOfRejected;
	private String accountIdOfRejected;
	private Double amountOfRejected;
	private String typeOfRejected;
	private int tenureOfRejected;
	private double roiOfRejected;
	private String statusOfRejected;
	private Double emiOfRejected;
	private int creditScoreOfRejected;

	private int loanDisbursedId;
	private int loanId1;
	private String accountId1;
	private Double disbursedAmount;
	private double dueAmount;
	private double emiToBePaid;
	private String loanType;

	@Override
	public List<Loan> retrieveLoanList() throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<Loan> reqList = new ArrayList<>();
		reqList = repository.findAll();
	}

	@Override
	public List<Loan> retrieveAcceptedLoanList() throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<Loan> reqList = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<LoanRequestEntity> cr = cb.createQuery(LoanRequestEntity.class);
			Root<LoanRequestEntity> root = cr.from(LoanRequestEntity.class);
			Predicate greaterThanCreditScore = cb.gt(root.get("creditScore"), 670);
			Predicate status = cb.like(root.get("status"), "Pending");
			cr.select(root).where(cb.and(greaterThanCreditScore, status));
			Query<LoanRequestEntity> query = session.createQuery(cr);
			List<LoanRequestEntity> results = query.list();
			reqList = acceptedLoanRequests(results);

		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
//		logger.info(Constants.SHOW_LOAN_REQUESTS[1]);
		return reqList;
	}

	@Override
	public List<Loan> retrieveRejectedLoanList() throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<Loan> reqList = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<LoanRequestEntity> cr = cb.createQuery(LoanRequestEntity.class);
			Root<LoanRequestEntity> root = cr.from(LoanRequestEntity.class);
			Predicate greaterThanCreditScore = cb.lt(root.get("creditScore"), 670);
			Predicate status = cb.like(root.get("status"), "Pending");
			cr.select(root).where(cb.and(greaterThanCreditScore, status));
			Query<LoanRequestEntity> query = session.createQuery(cr);
			List<LoanRequestEntity> results = query.list();
			reqList = rejectedLoanRequests(results);
		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
//		logger.info(Constants.SHOW_LOAN_REQUESTS[2]);
		return reqList;
	}

	@Override
	public void releaseLoanSheet(ArrayList<Loan> loanList) throws IOException, PecuniaException {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			for (Loan loans : loanList) {
				LoanDisbursalEntity loanDisbursalEntity = new LoanDisbursalEntity();
				loanDisbursalEntity.setLoanId(loans.getLoanId());
				loanDisbursalEntity.setAccountId(loans.getAccountId());
				loanDisbursalEntity.setDisbursedAmount(loans.getAmount());
				loanDisbursalEntity.setDueAmount(amountToBePaid(loans.getEmi(), loans.getTenure()));
				loanDisbursalEntity.setNumberOfEmiToBePaid(loans.getTenure());
				loanDisbursalEntity.setLoanType(loans.getType());
				session.save(loanDisbursalEntity);

			}
		}
	}

	@Override
	public ArrayList<LoanDisbursal> loanApprovedList() throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<LoanDisbursal> reqList = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<LoanDisbursalEntity> cr = cb.createQuery(LoanDisbursalEntity.class);
			Root<LoanDisbursalEntity> root = cr.from(LoanDisbursalEntity.class);
			cr.select(root);
			Query<LoanDisbursalEntity> query = session.createQuery(cr);
			List<LoanDisbursalEntity> results = query.list();
			reqList = loanDisbursal(results);
		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_LOAN_DISBURSAL_DATA);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_DISBURSAL_DATA);
		}
//		logger.info(Constants.SHOW_LOAN_DISBURSED_DATA);
		return reqList;
	}

	@Override
	public void updateLoanAccount(ArrayList<LoanDisbursal> loanApprovals, double dueAmount, double tenure,
			String accountId, int loanDisbursalId) throws IOException, PecuniaException, LoanDisbursalException {
		org.hibernate.Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			LoanDisbursalEntity loanDisbursalEntity = session.load(LoanDisbursalEntity.class, loanDisbursalId);
			loanDisbursalEntity.setDueAmount(dueAmount);
			loanDisbursalEntity.setNumberOfEmiToBePaid(tenure);
			session.update(loanDisbursalEntity);

			tx.commit();

		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_BALANCE_UPDATE);
			throw new LoanDisbursalException(ErrorConstants.NO_BALANCE_UPDATE);
		}

	}

	@Override
	public void updateStatus(ArrayList<Loan> loanRequests, int loanID, String Status)
			throws IOException, PecuniaException, LoanDisbursalException {
		org.hibernate.Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();

			LoanRequestEntity loanRequestEntity = session.load(LoanRequestEntity.class, loanID);

			loanRequestEntity.setStatus(Status);
			session.update(loanRequestEntity);

			tx.commit();

		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_LOAN_STATUS_UPDATE);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_STATUS_UPDATE);
		}

	}

	@Override
	public double totalEmi(String accountId) throws PecuniaException, LoanDisbursalException {
		double totalEMI = 0.0;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Double> cr = cb.createQuery(Double.class);
			Root<LoanRequestEntity> root = cr.from(LoanRequestEntity.class);
			cr.select(cb.sum(root.get("emi"))).where(cb.like(root.get("accountId"), accountId));
			TypedQuery<Double> query = session.createQuery(cr);
			totalEMI = query.getSingleResult();
		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_TOTAL_EMI);
			throw new LoanDisbursalException(ErrorConstants.NO_TOTAL_EMI);
		}
//		logger.info(Constants.TOTAL_EMI);
		return totalEMI;

	}

	@Override
	public List<Loan> retrieveAcceptedLoanListWithoutStatus()
			throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<Loan> reqList = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<LoanRequestEntity> cr = cb.createQuery(LoanRequestEntity.class);
			Root<LoanRequestEntity> root = cr.from(LoanRequestEntity.class);
			cr.select(root).where(cb.gt(root.get("creditScore"), 670));
			Query<LoanRequestEntity> query = session.createQuery(cr);
			List<LoanRequestEntity> results = query.list();
			reqList = acceptedLoanRequests(results);
		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_LOAN_REQUESTS);
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
//		logger.info(Constants.SHOW_LOAN_REQUESTS[1]);
		return reqList;
	}

	@Override
	public ArrayList<String> uniqueIds() throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<String> accountId = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<String> cr = cb.createQuery(String.class);
			Root<LoanDisbursalEntity> root = cr.from(LoanDisbursalEntity.class);
			cr.select(root.get("accountId")).distinct(true);
			TypedQuery<String> query = session.createQuery(cr);
			List<String> results = query.getResultList();
			for (String res : results) {
				accountId.add(res);
			}

		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_UNIQUE_IDS);
			throw new LoanDisbursalException(ErrorConstants.NO_UNIQUE_IDS);
		}
//		logger.info(Constants.UNIQUE_IDS);
		return accountId;
	}

	private ArrayList<Loan> loanRequests(List<LoanRequestEntity> res) {
		ArrayList<Loan> reqList = new ArrayList<>();
		for (LoanRequestEntity obj : res) {
			loanId = obj.getLoanId();
			accountId = obj.getAccountId();
			amount = obj.getAmount();
			type = obj.getType();
			tenure = obj.getTenure();
			roi = obj.getRoi();
			status = obj.getStatus();
			emi = obj.getEmi();
			creditScore = obj.getCreditScore();
			Loan loan = new Loan(loanId, accountId, amount, type, tenure, roi, status, emi, creditScore);
			reqList.add(loan);
		}

		return reqList;

	}

	private ArrayList<Loan> acceptedLoanRequests(List<LoanRequestEntity> res) {
		ArrayList<Loan> reqList = new ArrayList<>();
		for (LoanRequestEntity obj : res) {
			loanIdOfAccepted = obj.getLoanId();
			accountIdOfAccepted = obj.getAccountId();
			amountOfAccepted = obj.getAmount();
			typeOfAccepted = obj.getType();
			tenureOfAccepted = obj.getTenure();
			roiOfAccepted = obj.getRoi();
			statusOfAccepted = obj.getStatus();
			emiOfAccepted = obj.getEmi();
			creditScoreOfAccepted = obj.getCreditScore();
			Loan loan = new Loan(loanIdOfAccepted, accountIdOfAccepted, amountOfAccepted, typeOfAccepted,
					tenureOfAccepted, roiOfAccepted, statusOfAccepted, emiOfAccepted, creditScoreOfAccepted);
			reqList.add(loan);
		}

		return reqList;

	}

	private ArrayList<Loan> rejectedLoanRequests(List<LoanRequestEntity> res) {
		ArrayList<Loan> reqList = new ArrayList<>();
		for (LoanRequestEntity obj : res) {
			loanIdOfRejected = obj.getLoanId();
			accountIdOfRejected = obj.getAccountId();
			amountOfRejected = obj.getAmount();
			typeOfRejected = obj.getType();
			tenureOfRejected = obj.getTenure();
			roiOfRejected = obj.getRoi();
			statusOfRejected = obj.getStatus();
			emiOfRejected = obj.getEmi();
			creditScoreOfRejected = obj.getCreditScore();
			Loan loan = new Loan(loanIdOfRejected, accountIdOfRejected, amountOfRejected, typeOfRejected,
					tenureOfRejected, roiOfRejected, statusOfRejected, emiOfRejected, creditScoreOfRejected);
			reqList.add(loan);
		}

		return reqList;

	}

	private ArrayList<LoanDisbursal> loanDisbursal(List<LoanDisbursalEntity> res) {
		ArrayList<LoanDisbursal> reqList = new ArrayList<>();
		for (LoanDisbursalEntity obj : res) {
			loanDisbursedId = obj.getLoanDisbursalId();
			loanId1 = obj.getLoanId();
			accountId1 = obj.getAccountId();
			disbursedAmount = obj.getDisbursedAmount();
			dueAmount = obj.getDueAmount();
			emiToBePaid = obj.getNumberOfEmiToBePaid();
			loanType = obj.getLoanType();

			LoanDisbursal getDetails = new LoanDisbursal(loanDisbursedId, loanId1, accountId1, disbursedAmount,
					dueAmount, emiToBePaid, loanType);
			reqList.add(getDetails);
		}

		return reqList;

	}

	private double amountToBePaid(double emi, int tenure) {
		return emi * tenure;
	}

}
