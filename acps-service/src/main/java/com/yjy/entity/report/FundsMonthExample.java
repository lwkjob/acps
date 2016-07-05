package com.yjy.entity.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FundsMonthExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FundsMonthExample() {
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

        public Criteria andCuryearIsNull() {
            addCriterion("CurYear is null");
            return (Criteria) this;
        }

        public Criteria andCuryearIsNotNull() {
            addCriterion("CurYear is not null");
            return (Criteria) this;
        }

        public Criteria andCuryearEqualTo(Integer value) {
            addCriterion("CurYear =", value, "curyear");
            return (Criteria) this;
        }

        public Criteria andCuryearNotEqualTo(Integer value) {
            addCriterion("CurYear <>", value, "curyear");
            return (Criteria) this;
        }

        public Criteria andCuryearGreaterThan(Integer value) {
            addCriterion("CurYear >", value, "curyear");
            return (Criteria) this;
        }

        public Criteria andCuryearGreaterThanOrEqualTo(Integer value) {
            addCriterion("CurYear >=", value, "curyear");
            return (Criteria) this;
        }

        public Criteria andCuryearLessThan(Integer value) {
            addCriterion("CurYear <", value, "curyear");
            return (Criteria) this;
        }

        public Criteria andCuryearLessThanOrEqualTo(Integer value) {
            addCriterion("CurYear <=", value, "curyear");
            return (Criteria) this;
        }

        public Criteria andCuryearIn(List<Integer> values) {
            addCriterion("CurYear in", values, "curyear");
            return (Criteria) this;
        }

        public Criteria andCuryearNotIn(List<Integer> values) {
            addCriterion("CurYear not in", values, "curyear");
            return (Criteria) this;
        }

        public Criteria andCuryearBetween(Integer value1, Integer value2) {
            addCriterion("CurYear between", value1, value2, "curyear");
            return (Criteria) this;
        }

        public Criteria andCuryearNotBetween(Integer value1, Integer value2) {
            addCriterion("CurYear not between", value1, value2, "curyear");
            return (Criteria) this;
        }

        public Criteria andCurmonthIsNull() {
            addCriterion("CurMonth is null");
            return (Criteria) this;
        }

        public Criteria andCurmonthIsNotNull() {
            addCriterion("CurMonth is not null");
            return (Criteria) this;
        }

        public Criteria andCurmonthEqualTo(Integer value) {
            addCriterion("CurMonth =", value, "curmonth");
            return (Criteria) this;
        }

        public Criteria andCurmonthNotEqualTo(Integer value) {
            addCriterion("CurMonth <>", value, "curmonth");
            return (Criteria) this;
        }

        public Criteria andCurmonthGreaterThan(Integer value) {
            addCriterion("CurMonth >", value, "curmonth");
            return (Criteria) this;
        }

        public Criteria andCurmonthGreaterThanOrEqualTo(Integer value) {
            addCriterion("CurMonth >=", value, "curmonth");
            return (Criteria) this;
        }

        public Criteria andCurmonthLessThan(Integer value) {
            addCriterion("CurMonth <", value, "curmonth");
            return (Criteria) this;
        }

        public Criteria andCurmonthLessThanOrEqualTo(Integer value) {
            addCriterion("CurMonth <=", value, "curmonth");
            return (Criteria) this;
        }

        public Criteria andCurmonthIn(List<Integer> values) {
            addCriterion("CurMonth in", values, "curmonth");
            return (Criteria) this;
        }

        public Criteria andCurmonthNotIn(List<Integer> values) {
            addCriterion("CurMonth not in", values, "curmonth");
            return (Criteria) this;
        }

        public Criteria andCurmonthBetween(Integer value1, Integer value2) {
            addCriterion("CurMonth between", value1, value2, "curmonth");
            return (Criteria) this;
        }

        public Criteria andCurmonthNotBetween(Integer value1, Integer value2) {
            addCriterion("CurMonth not between", value1, value2, "curmonth");
            return (Criteria) this;
        }

        public Criteria andSaleridIsNull() {
            addCriterion("SalerId is null");
            return (Criteria) this;
        }

        public Criteria andSaleridIsNotNull() {
            addCriterion("SalerId is not null");
            return (Criteria) this;
        }

        public Criteria andSaleridEqualTo(Integer value) {
            addCriterion("SalerId =", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridNotEqualTo(Integer value) {
            addCriterion("SalerId <>", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridGreaterThan(Integer value) {
            addCriterion("SalerId >", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridGreaterThanOrEqualTo(Integer value) {
            addCriterion("SalerId >=", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridLessThan(Integer value) {
            addCriterion("SalerId <", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridLessThanOrEqualTo(Integer value) {
            addCriterion("SalerId <=", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridIn(List<Integer> values) {
            addCriterion("SalerId in", values, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridNotIn(List<Integer> values) {
            addCriterion("SalerId not in", values, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridBetween(Integer value1, Integer value2) {
            addCriterion("SalerId between", value1, value2, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridNotBetween(Integer value1, Integer value2) {
            addCriterion("SalerId not between", value1, value2, "salerid");
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

        public Criteria andCurreceiptpriceIsNull() {
            addCriterion("CurReceiptPrice is null");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceIsNotNull() {
            addCriterion("CurReceiptPrice is not null");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceEqualTo(BigDecimal value) {
            addCriterion("CurReceiptPrice =", value, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceNotEqualTo(BigDecimal value) {
            addCriterion("CurReceiptPrice <>", value, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceGreaterThan(BigDecimal value) {
            addCriterion("CurReceiptPrice >", value, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CurReceiptPrice >=", value, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceLessThan(BigDecimal value) {
            addCriterion("CurReceiptPrice <", value, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CurReceiptPrice <=", value, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceIn(List<BigDecimal> values) {
            addCriterion("CurReceiptPrice in", values, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceNotIn(List<BigDecimal> values) {
            addCriterion("CurReceiptPrice not in", values, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurReceiptPrice between", value1, value2, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurreceiptpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurReceiptPrice not between", value1, value2, "curreceiptprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceIsNull() {
            addCriterion("CurRedPrice is null");
            return (Criteria) this;
        }

        public Criteria andCurredpriceIsNotNull() {
            addCriterion("CurRedPrice is not null");
            return (Criteria) this;
        }

        public Criteria andCurredpriceEqualTo(BigDecimal value) {
            addCriterion("CurRedPrice =", value, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceNotEqualTo(BigDecimal value) {
            addCriterion("CurRedPrice <>", value, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceGreaterThan(BigDecimal value) {
            addCriterion("CurRedPrice >", value, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CurRedPrice >=", value, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceLessThan(BigDecimal value) {
            addCriterion("CurRedPrice <", value, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CurRedPrice <=", value, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceIn(List<BigDecimal> values) {
            addCriterion("CurRedPrice in", values, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceNotIn(List<BigDecimal> values) {
            addCriterion("CurRedPrice not in", values, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurRedPrice between", value1, value2, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurredpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurRedPrice not between", value1, value2, "curredprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceIsNull() {
            addCriterion("CurMarkupPrice is null");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceIsNotNull() {
            addCriterion("CurMarkupPrice is not null");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceEqualTo(BigDecimal value) {
            addCriterion("CurMarkupPrice =", value, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceNotEqualTo(BigDecimal value) {
            addCriterion("CurMarkupPrice <>", value, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceGreaterThan(BigDecimal value) {
            addCriterion("CurMarkupPrice >", value, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CurMarkupPrice >=", value, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceLessThan(BigDecimal value) {
            addCriterion("CurMarkupPrice <", value, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CurMarkupPrice <=", value, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceIn(List<BigDecimal> values) {
            addCriterion("CurMarkupPrice in", values, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceNotIn(List<BigDecimal> values) {
            addCriterion("CurMarkupPrice not in", values, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurMarkupPrice between", value1, value2, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCurmarkuppriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurMarkupPrice not between", value1, value2, "curmarkupprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceIsNull() {
            addCriterion("CurSettlePrice is null");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceIsNotNull() {
            addCriterion("CurSettlePrice is not null");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceEqualTo(BigDecimal value) {
            addCriterion("CurSettlePrice =", value, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceNotEqualTo(BigDecimal value) {
            addCriterion("CurSettlePrice <>", value, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceGreaterThan(BigDecimal value) {
            addCriterion("CurSettlePrice >", value, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CurSettlePrice >=", value, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceLessThan(BigDecimal value) {
            addCriterion("CurSettlePrice <", value, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CurSettlePrice <=", value, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceIn(List<BigDecimal> values) {
            addCriterion("CurSettlePrice in", values, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceNotIn(List<BigDecimal> values) {
            addCriterion("CurSettlePrice not in", values, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurSettlePrice between", value1, value2, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCursettlepriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurSettlePrice not between", value1, value2, "cursettleprice");
            return (Criteria) this;
        }

        public Criteria andCurbalanceIsNull() {
            addCriterion("CurBalance is null");
            return (Criteria) this;
        }

        public Criteria andCurbalanceIsNotNull() {
            addCriterion("CurBalance is not null");
            return (Criteria) this;
        }

        public Criteria andCurbalanceEqualTo(BigDecimal value) {
            addCriterion("CurBalance =", value, "curbalance");
            return (Criteria) this;
        }

        public Criteria andCurbalanceNotEqualTo(BigDecimal value) {
            addCriterion("CurBalance <>", value, "curbalance");
            return (Criteria) this;
        }

        public Criteria andCurbalanceGreaterThan(BigDecimal value) {
            addCriterion("CurBalance >", value, "curbalance");
            return (Criteria) this;
        }

        public Criteria andCurbalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CurBalance >=", value, "curbalance");
            return (Criteria) this;
        }

        public Criteria andCurbalanceLessThan(BigDecimal value) {
            addCriterion("CurBalance <", value, "curbalance");
            return (Criteria) this;
        }

        public Criteria andCurbalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CurBalance <=", value, "curbalance");
            return (Criteria) this;
        }

        public Criteria andCurbalanceIn(List<BigDecimal> values) {
            addCriterion("CurBalance in", values, "curbalance");
            return (Criteria) this;
        }

        public Criteria andCurbalanceNotIn(List<BigDecimal> values) {
            addCriterion("CurBalance not in", values, "curbalance");
            return (Criteria) this;
        }

        public Criteria andCurbalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurBalance between", value1, value2, "curbalance");
            return (Criteria) this;
        }

        public Criteria andCurbalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CurBalance not between", value1, value2, "curbalance");
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