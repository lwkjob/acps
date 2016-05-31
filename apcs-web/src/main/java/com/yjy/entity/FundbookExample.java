package com.yjy.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FundbookExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FundbookExample() {
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

        public Criteria andEntryidIsNull() {
            addCriterion("EntryId is null");
            return (Criteria) this;
        }

        public Criteria andEntryidIsNotNull() {
            addCriterion("EntryId is not null");
            return (Criteria) this;
        }

        public Criteria andEntryidEqualTo(Long value) {
            addCriterion("EntryId =", value, "entryid");
            return (Criteria) this;
        }

        public Criteria andEntryidNotEqualTo(Long value) {
            addCriterion("EntryId <>", value, "entryid");
            return (Criteria) this;
        }

        public Criteria andEntryidGreaterThan(Long value) {
            addCriterion("EntryId >", value, "entryid");
            return (Criteria) this;
        }

        public Criteria andEntryidGreaterThanOrEqualTo(Long value) {
            addCriterion("EntryId >=", value, "entryid");
            return (Criteria) this;
        }

        public Criteria andEntryidLessThan(Long value) {
            addCriterion("EntryId <", value, "entryid");
            return (Criteria) this;
        }

        public Criteria andEntryidLessThanOrEqualTo(Long value) {
            addCriterion("EntryId <=", value, "entryid");
            return (Criteria) this;
        }

        public Criteria andEntryidIn(List<Long> values) {
            addCriterion("EntryId in", values, "entryid");
            return (Criteria) this;
        }

        public Criteria andEntryidNotIn(List<Long> values) {
            addCriterion("EntryId not in", values, "entryid");
            return (Criteria) this;
        }

        public Criteria andEntryidBetween(Long value1, Long value2) {
            addCriterion("EntryId between", value1, value2, "entryid");
            return (Criteria) this;
        }

        public Criteria andEntryidNotBetween(Long value1, Long value2) {
            addCriterion("EntryId not between", value1, value2, "entryid");
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

        public Criteria andEntrynumberIsNull() {
            addCriterion("EntryNumber is null");
            return (Criteria) this;
        }

        public Criteria andEntrynumberIsNotNull() {
            addCriterion("EntryNumber is not null");
            return (Criteria) this;
        }

        public Criteria andEntrynumberEqualTo(String value) {
            addCriterion("EntryNumber =", value, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberNotEqualTo(String value) {
            addCriterion("EntryNumber <>", value, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberGreaterThan(String value) {
            addCriterion("EntryNumber >", value, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberGreaterThanOrEqualTo(String value) {
            addCriterion("EntryNumber >=", value, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberLessThan(String value) {
            addCriterion("EntryNumber <", value, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberLessThanOrEqualTo(String value) {
            addCriterion("EntryNumber <=", value, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberLike(String value) {
            addCriterion("EntryNumber like", value, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberNotLike(String value) {
            addCriterion("EntryNumber not like", value, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberIn(List<String> values) {
            addCriterion("EntryNumber in", values, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberNotIn(List<String> values) {
            addCriterion("EntryNumber not in", values, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberBetween(String value1, String value2) {
            addCriterion("EntryNumber between", value1, value2, "entrynumber");
            return (Criteria) this;
        }

        public Criteria andEntrynumberNotBetween(String value1, String value2) {
            addCriterion("EntryNumber not between", value1, value2, "entrynumber");
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

        public Criteria andInvoicenumberIsNull() {
            addCriterion("InvoiceNumber is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberIsNotNull() {
            addCriterion("InvoiceNumber is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberEqualTo(String value) {
            addCriterion("InvoiceNumber =", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberNotEqualTo(String value) {
            addCriterion("InvoiceNumber <>", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberGreaterThan(String value) {
            addCriterion("InvoiceNumber >", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceNumber >=", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberLessThan(String value) {
            addCriterion("InvoiceNumber <", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberLessThanOrEqualTo(String value) {
            addCriterion("InvoiceNumber <=", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberLike(String value) {
            addCriterion("InvoiceNumber like", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberNotLike(String value) {
            addCriterion("InvoiceNumber not like", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberIn(List<String> values) {
            addCriterion("InvoiceNumber in", values, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberNotIn(List<String> values) {
            addCriterion("InvoiceNumber not in", values, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberBetween(String value1, String value2) {
            addCriterion("InvoiceNumber between", value1, value2, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberNotBetween(String value1, String value2) {
            addCriterion("InvoiceNumber not between", value1, value2, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andHappentimeIsNull() {
            addCriterion("HappenTime is null");
            return (Criteria) this;
        }

        public Criteria andHappentimeIsNotNull() {
            addCriterion("HappenTime is not null");
            return (Criteria) this;
        }

        public Criteria andHappentimeEqualTo(Long value) {
            addCriterion("HappenTime =", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeNotEqualTo(Long value) {
            addCriterion("HappenTime <>", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeGreaterThan(Long value) {
            addCriterion("HappenTime >", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeGreaterThanOrEqualTo(Long value) {
            addCriterion("HappenTime >=", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeLessThan(Long value) {
            addCriterion("HappenTime <", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeLessThanOrEqualTo(Long value) {
            addCriterion("HappenTime <=", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeIn(List<Long> values) {
            addCriterion("HappenTime in", values, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeNotIn(List<Long> values) {
            addCriterion("HappenTime not in", values, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeBetween(Long value1, Long value2) {
            addCriterion("HappenTime between", value1, value2, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeNotBetween(Long value1, Long value2) {
            addCriterion("HappenTime not between", value1, value2, "happentime");
            return (Criteria) this;
        }

        public Criteria andGoodsIsNull() {
            addCriterion("Goods is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIsNotNull() {
            addCriterion("Goods is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsEqualTo(BigDecimal value) {
            addCriterion("Goods =", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotEqualTo(BigDecimal value) {
            addCriterion("Goods <>", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsGreaterThan(BigDecimal value) {
            addCriterion("Goods >", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Goods >=", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsLessThan(BigDecimal value) {
            addCriterion("Goods <", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Goods <=", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsIn(List<BigDecimal> values) {
            addCriterion("Goods in", values, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotIn(List<BigDecimal> values) {
            addCriterion("Goods not in", values, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Goods between", value1, value2, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Goods not between", value1, value2, "goods");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNull() {
            addCriterion("Money is null");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNotNull() {
            addCriterion("Money is not null");
            return (Criteria) this;
        }

        public Criteria andMoneyEqualTo(BigDecimal value) {
            addCriterion("Money =", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotEqualTo(BigDecimal value) {
            addCriterion("Money <>", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThan(BigDecimal value) {
            addCriterion("Money >", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Money >=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThan(BigDecimal value) {
            addCriterion("Money <", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Money <=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyIn(List<BigDecimal> values) {
            addCriterion("Money in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotIn(List<BigDecimal> values) {
            addCriterion("Money not in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Money between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Money not between", value1, value2, "money");
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