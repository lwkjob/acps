package com.yjy.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FundbookdayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FundbookdayExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBookidIsNull() {
            addCriterion("BookId is null");
            return (Criteria) this;
        }

        public Criteria andBookidIsNotNull() {
            addCriterion("BookId is not null");
            return (Criteria) this;
        }

        public Criteria andBookidEqualTo(Long value) {
            addCriterion("BookId =", value, "bookid");
            return (Criteria) this;
        }

        public Criteria andBookidNotEqualTo(Long value) {
            addCriterion("BookId <>", value, "bookid");
            return (Criteria) this;
        }

        public Criteria andBookidGreaterThan(Long value) {
            addCriterion("BookId >", value, "bookid");
            return (Criteria) this;
        }

        public Criteria andBookidGreaterThanOrEqualTo(Long value) {
            addCriterion("BookId >=", value, "bookid");
            return (Criteria) this;
        }

        public Criteria andBookidLessThan(Long value) {
            addCriterion("BookId <", value, "bookid");
            return (Criteria) this;
        }

        public Criteria andBookidLessThanOrEqualTo(Long value) {
            addCriterion("BookId <=", value, "bookid");
            return (Criteria) this;
        }

        public Criteria andBookidIn(List<Long> values) {
            addCriterion("BookId in", values, "bookid");
            return (Criteria) this;
        }

        public Criteria andBookidNotIn(List<Long> values) {
            addCriterion("BookId not in", values, "bookid");
            return (Criteria) this;
        }

        public Criteria andBookidBetween(Long value1, Long value2) {
            addCriterion("BookId between", value1, value2, "bookid");
            return (Criteria) this;
        }

        public Criteria andBookidNotBetween(Long value1, Long value2) {
            addCriterion("BookId not between", value1, value2, "bookid");
            return (Criteria) this;
        }

        public Criteria andPlatformroleIsNull() {
            addCriterion("PlatformRole is null");
            return (Criteria) this;
        }

        public Criteria andPlatformroleIsNotNull() {
            addCriterion("PlatformRole is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformroleEqualTo(Integer value) {
            addCriterion("PlatformRole =", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleNotEqualTo(Integer value) {
            addCriterion("PlatformRole <>", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleGreaterThan(Integer value) {
            addCriterion("PlatformRole >", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleGreaterThanOrEqualTo(Integer value) {
            addCriterion("PlatformRole >=", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleLessThan(Integer value) {
            addCriterion("PlatformRole <", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleLessThanOrEqualTo(Integer value) {
            addCriterion("PlatformRole <=", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleIn(List<Integer> values) {
            addCriterion("PlatformRole in", values, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleNotIn(List<Integer> values) {
            addCriterion("PlatformRole not in", values, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleBetween(Integer value1, Integer value2) {
            addCriterion("PlatformRole between", value1, value2, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleNotBetween(Integer value1, Integer value2) {
            addCriterion("PlatformRole not between", value1, value2, "platformrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleIsNull() {
            addCriterion("EntryUserRole is null");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleIsNotNull() {
            addCriterion("EntryUserRole is not null");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleEqualTo(Integer value) {
            addCriterion("EntryUserRole =", value, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleNotEqualTo(Integer value) {
            addCriterion("EntryUserRole <>", value, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleGreaterThan(Integer value) {
            addCriterion("EntryUserRole >", value, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleGreaterThanOrEqualTo(Integer value) {
            addCriterion("EntryUserRole >=", value, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleLessThan(Integer value) {
            addCriterion("EntryUserRole <", value, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleLessThanOrEqualTo(Integer value) {
            addCriterion("EntryUserRole <=", value, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleIn(List<Integer> values) {
            addCriterion("EntryUserRole in", values, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleNotIn(List<Integer> values) {
            addCriterion("EntryUserRole not in", values, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleBetween(Integer value1, Integer value2) {
            addCriterion("EntryUserRole between", value1, value2, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andEntryuserroleNotBetween(Integer value1, Integer value2) {
            addCriterion("EntryUserRole not between", value1, value2, "entryuserrole");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberIsNull() {
            addCriterion("AccBookNumber is null");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberIsNotNull() {
            addCriterion("AccBookNumber is not null");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberEqualTo(Integer value) {
            addCriterion("AccBookNumber =", value, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberNotEqualTo(Integer value) {
            addCriterion("AccBookNumber <>", value, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberGreaterThan(Integer value) {
            addCriterion("AccBookNumber >", value, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("AccBookNumber >=", value, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberLessThan(Integer value) {
            addCriterion("AccBookNumber <", value, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberLessThanOrEqualTo(Integer value) {
            addCriterion("AccBookNumber <=", value, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberIn(List<Integer> values) {
            addCriterion("AccBookNumber in", values, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberNotIn(List<Integer> values) {
            addCriterion("AccBookNumber not in", values, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberBetween(Integer value1, Integer value2) {
            addCriterion("AccBookNumber between", value1, value2, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andAccbooknumberNotBetween(Integer value1, Integer value2) {
            addCriterion("AccBookNumber not between", value1, value2, "accbooknumber");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("UserId is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("UserId is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("UserId =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("UserId <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("UserId >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("UserId >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("UserId <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("UserId <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("UserId in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("UserId not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("UserId between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("UserId not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andAreacodeIsNull() {
            addCriterion("AreaCode is null");
            return (Criteria) this;
        }

        public Criteria andAreacodeIsNotNull() {
            addCriterion("AreaCode is not null");
            return (Criteria) this;
        }

        public Criteria andAreacodeEqualTo(Integer value) {
            addCriterion("AreaCode =", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotEqualTo(Integer value) {
            addCriterion("AreaCode <>", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThan(Integer value) {
            addCriterion("AreaCode >", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("AreaCode >=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThan(Integer value) {
            addCriterion("AreaCode <", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThanOrEqualTo(Integer value) {
            addCriterion("AreaCode <=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeIn(List<Integer> values) {
            addCriterion("AreaCode in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotIn(List<Integer> values) {
            addCriterion("AreaCode not in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeBetween(Integer value1, Integer value2) {
            addCriterion("AreaCode between", value1, value2, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotBetween(Integer value1, Integer value2) {
            addCriterion("AreaCode not between", value1, value2, "areacode");
            return (Criteria) this;
        }

        public Criteria andBookdateIsNull() {
            addCriterion("BookDate is null");
            return (Criteria) this;
        }

        public Criteria andBookdateIsNotNull() {
            addCriterion("BookDate is not null");
            return (Criteria) this;
        }

        public Criteria andBookdateEqualTo(Integer value) {
            addCriterion("BookDate =", value, "bookdate");
            return (Criteria) this;
        }

        public Criteria andBookdateNotEqualTo(Integer value) {
            addCriterion("BookDate <>", value, "bookdate");
            return (Criteria) this;
        }

        public Criteria andBookdateGreaterThan(Integer value) {
            addCriterion("BookDate >", value, "bookdate");
            return (Criteria) this;
        }

        public Criteria andBookdateGreaterThanOrEqualTo(Integer value) {
            addCriterion("BookDate >=", value, "bookdate");
            return (Criteria) this;
        }

        public Criteria andBookdateLessThan(Integer value) {
            addCriterion("BookDate <", value, "bookdate");
            return (Criteria) this;
        }

        public Criteria andBookdateLessThanOrEqualTo(Integer value) {
            addCriterion("BookDate <=", value, "bookdate");
            return (Criteria) this;
        }

        public Criteria andBookdateIn(List<Integer> values) {
            addCriterion("BookDate in", values, "bookdate");
            return (Criteria) this;
        }

        public Criteria andBookdateNotIn(List<Integer> values) {
            addCriterion("BookDate not in", values, "bookdate");
            return (Criteria) this;
        }

        public Criteria andBookdateBetween(Integer value1, Integer value2) {
            addCriterion("BookDate between", value1, value2, "bookdate");
            return (Criteria) this;
        }

        public Criteria andBookdateNotBetween(Integer value1, Integer value2) {
            addCriterion("BookDate not between", value1, value2, "bookdate");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceIsNull() {
            addCriterion("PrevBalance is null");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceIsNotNull() {
            addCriterion("PrevBalance is not null");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceEqualTo(BigDecimal value) {
            addCriterion("PrevBalance =", value, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceNotEqualTo(BigDecimal value) {
            addCriterion("PrevBalance <>", value, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceGreaterThan(BigDecimal value) {
            addCriterion("PrevBalance >", value, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PrevBalance >=", value, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceLessThan(BigDecimal value) {
            addCriterion("PrevBalance <", value, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PrevBalance <=", value, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceIn(List<BigDecimal> values) {
            addCriterion("PrevBalance in", values, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceNotIn(List<BigDecimal> values) {
            addCriterion("PrevBalance not in", values, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PrevBalance between", value1, value2, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andPrevbalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PrevBalance not between", value1, value2, "prevbalance");
            return (Criteria) this;
        }

        public Criteria andHappendebitIsNull() {
            addCriterion("HappenDebit is null");
            return (Criteria) this;
        }

        public Criteria andHappendebitIsNotNull() {
            addCriterion("HappenDebit is not null");
            return (Criteria) this;
        }

        public Criteria andHappendebitEqualTo(BigDecimal value) {
            addCriterion("HappenDebit =", value, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappendebitNotEqualTo(BigDecimal value) {
            addCriterion("HappenDebit <>", value, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappendebitGreaterThan(BigDecimal value) {
            addCriterion("HappenDebit >", value, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappendebitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("HappenDebit >=", value, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappendebitLessThan(BigDecimal value) {
            addCriterion("HappenDebit <", value, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappendebitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("HappenDebit <=", value, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappendebitIn(List<BigDecimal> values) {
            addCriterion("HappenDebit in", values, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappendebitNotIn(List<BigDecimal> values) {
            addCriterion("HappenDebit not in", values, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappendebitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HappenDebit between", value1, value2, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappendebitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HappenDebit not between", value1, value2, "happendebit");
            return (Criteria) this;
        }

        public Criteria andHappencreditIsNull() {
            addCriterion("HappenCredit is null");
            return (Criteria) this;
        }

        public Criteria andHappencreditIsNotNull() {
            addCriterion("HappenCredit is not null");
            return (Criteria) this;
        }

        public Criteria andHappencreditEqualTo(BigDecimal value) {
            addCriterion("HappenCredit =", value, "happencredit");
            return (Criteria) this;
        }

        public Criteria andHappencreditNotEqualTo(BigDecimal value) {
            addCriterion("HappenCredit <>", value, "happencredit");
            return (Criteria) this;
        }

        public Criteria andHappencreditGreaterThan(BigDecimal value) {
            addCriterion("HappenCredit >", value, "happencredit");
            return (Criteria) this;
        }

        public Criteria andHappencreditGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("HappenCredit >=", value, "happencredit");
            return (Criteria) this;
        }

        public Criteria andHappencreditLessThan(BigDecimal value) {
            addCriterion("HappenCredit <", value, "happencredit");
            return (Criteria) this;
        }

        public Criteria andHappencreditLessThanOrEqualTo(BigDecimal value) {
            addCriterion("HappenCredit <=", value, "happencredit");
            return (Criteria) this;
        }

        public Criteria andHappencreditIn(List<BigDecimal> values) {
            addCriterion("HappenCredit in", values, "happencredit");
            return (Criteria) this;
        }

        public Criteria andHappencreditNotIn(List<BigDecimal> values) {
            addCriterion("HappenCredit not in", values, "happencredit");
            return (Criteria) this;
        }

        public Criteria andHappencreditBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HappenCredit between", value1, value2, "happencredit");
            return (Criteria) this;
        }

        public Criteria andHappencreditNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("HappenCredit not between", value1, value2, "happencredit");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("Balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("Balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(BigDecimal value) {
            addCriterion("Balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(BigDecimal value) {
            addCriterion("Balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(BigDecimal value) {
            addCriterion("Balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(BigDecimal value) {
            addCriterion("Balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<BigDecimal> values) {
            addCriterion("Balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<BigDecimal> values) {
            addCriterion("Balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Balance not between", value1, value2, "balance");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}