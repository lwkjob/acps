package com.yjy.entity;

import java.util.ArrayList;
import java.util.List;

public class BookcodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BookcodeExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFundtypeIsNull() {
            addCriterion("fundtype is null");
            return (Criteria) this;
        }

        public Criteria andFundtypeIsNotNull() {
            addCriterion("fundtype is not null");
            return (Criteria) this;
        }

        public Criteria andFundtypeEqualTo(Integer value) {
            addCriterion("fundtype =", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeNotEqualTo(Integer value) {
            addCriterion("fundtype <>", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeGreaterThan(Integer value) {
            addCriterion("fundtype >", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("fundtype >=", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeLessThan(Integer value) {
            addCriterion("fundtype <", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeLessThanOrEqualTo(Integer value) {
            addCriterion("fundtype <=", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeIn(List<Integer> values) {
            addCriterion("fundtype in", values, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeNotIn(List<Integer> values) {
            addCriterion("fundtype not in", values, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeBetween(Integer value1, Integer value2) {
            addCriterion("fundtype between", value1, value2, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("fundtype not between", value1, value2, "fundtype");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneIsNull() {
            addCriterion("BookCodeOne is null");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneIsNotNull() {
            addCriterion("BookCodeOne is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneEqualTo(Integer value) {
            addCriterion("BookCodeOne =", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneNotEqualTo(Integer value) {
            addCriterion("BookCodeOne <>", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneGreaterThan(Integer value) {
            addCriterion("BookCodeOne >", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneGreaterThanOrEqualTo(Integer value) {
            addCriterion("BookCodeOne >=", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneLessThan(Integer value) {
            addCriterion("BookCodeOne <", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneLessThanOrEqualTo(Integer value) {
            addCriterion("BookCodeOne <=", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneIn(List<Integer> values) {
            addCriterion("BookCodeOne in", values, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneNotIn(List<Integer> values) {
            addCriterion("BookCodeOne not in", values, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeOne between", value1, value2, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneNotBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeOne not between", value1, value2, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoIsNull() {
            addCriterion("BookCodeTwo is null");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoIsNotNull() {
            addCriterion("BookCodeTwo is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoEqualTo(Integer value) {
            addCriterion("BookCodeTwo =", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoNotEqualTo(Integer value) {
            addCriterion("BookCodeTwo <>", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoGreaterThan(Integer value) {
            addCriterion("BookCodeTwo >", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoGreaterThanOrEqualTo(Integer value) {
            addCriterion("BookCodeTwo >=", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoLessThan(Integer value) {
            addCriterion("BookCodeTwo <", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoLessThanOrEqualTo(Integer value) {
            addCriterion("BookCodeTwo <=", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoIn(List<Integer> values) {
            addCriterion("BookCodeTwo in", values, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoNotIn(List<Integer> values) {
            addCriterion("BookCodeTwo not in", values, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeTwo between", value1, value2, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoNotBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeTwo not between", value1, value2, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeIsNull() {
            addCriterion("BookCodeThree is null");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeIsNotNull() {
            addCriterion("BookCodeThree is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeEqualTo(Integer value) {
            addCriterion("BookCodeThree =", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeNotEqualTo(Integer value) {
            addCriterion("BookCodeThree <>", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeGreaterThan(Integer value) {
            addCriterion("BookCodeThree >", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeGreaterThanOrEqualTo(Integer value) {
            addCriterion("BookCodeThree >=", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeLessThan(Integer value) {
            addCriterion("BookCodeThree <", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeLessThanOrEqualTo(Integer value) {
            addCriterion("BookCodeThree <=", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeIn(List<Integer> values) {
            addCriterion("BookCodeThree in", values, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeNotIn(List<Integer> values) {
            addCriterion("BookCodeThree not in", values, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeThree between", value1, value2, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeNotBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeThree not between", value1, value2, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescIsNull() {
            addCriterion("BookCodeOneDesc is null");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescIsNotNull() {
            addCriterion("BookCodeOneDesc is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescEqualTo(String value) {
            addCriterion("BookCodeOneDesc =", value, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescNotEqualTo(String value) {
            addCriterion("BookCodeOneDesc <>", value, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescGreaterThan(String value) {
            addCriterion("BookCodeOneDesc >", value, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescGreaterThanOrEqualTo(String value) {
            addCriterion("BookCodeOneDesc >=", value, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescLessThan(String value) {
            addCriterion("BookCodeOneDesc <", value, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescLessThanOrEqualTo(String value) {
            addCriterion("BookCodeOneDesc <=", value, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescLike(String value) {
            addCriterion("BookCodeOneDesc like", value, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescNotLike(String value) {
            addCriterion("BookCodeOneDesc not like", value, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescIn(List<String> values) {
            addCriterion("BookCodeOneDesc in", values, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescNotIn(List<String> values) {
            addCriterion("BookCodeOneDesc not in", values, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescBetween(String value1, String value2) {
            addCriterion("BookCodeOneDesc between", value1, value2, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodeonedescNotBetween(String value1, String value2) {
            addCriterion("BookCodeOneDesc not between", value1, value2, "bookcodeonedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescIsNull() {
            addCriterion("BookCodeTwoDesc is null");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescIsNotNull() {
            addCriterion("BookCodeTwoDesc is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescEqualTo(String value) {
            addCriterion("BookCodeTwoDesc =", value, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescNotEqualTo(String value) {
            addCriterion("BookCodeTwoDesc <>", value, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescGreaterThan(String value) {
            addCriterion("BookCodeTwoDesc >", value, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescGreaterThanOrEqualTo(String value) {
            addCriterion("BookCodeTwoDesc >=", value, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescLessThan(String value) {
            addCriterion("BookCodeTwoDesc <", value, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescLessThanOrEqualTo(String value) {
            addCriterion("BookCodeTwoDesc <=", value, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescLike(String value) {
            addCriterion("BookCodeTwoDesc like", value, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescNotLike(String value) {
            addCriterion("BookCodeTwoDesc not like", value, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescIn(List<String> values) {
            addCriterion("BookCodeTwoDesc in", values, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescNotIn(List<String> values) {
            addCriterion("BookCodeTwoDesc not in", values, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescBetween(String value1, String value2) {
            addCriterion("BookCodeTwoDesc between", value1, value2, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodetwodescNotBetween(String value1, String value2) {
            addCriterion("BookCodeTwoDesc not between", value1, value2, "bookcodetwodesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescIsNull() {
            addCriterion("BookCodeThreeDesc is null");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescIsNotNull() {
            addCriterion("BookCodeThreeDesc is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescEqualTo(String value) {
            addCriterion("BookCodeThreeDesc =", value, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescNotEqualTo(String value) {
            addCriterion("BookCodeThreeDesc <>", value, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescGreaterThan(String value) {
            addCriterion("BookCodeThreeDesc >", value, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescGreaterThanOrEqualTo(String value) {
            addCriterion("BookCodeThreeDesc >=", value, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescLessThan(String value) {
            addCriterion("BookCodeThreeDesc <", value, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescLessThanOrEqualTo(String value) {
            addCriterion("BookCodeThreeDesc <=", value, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescLike(String value) {
            addCriterion("BookCodeThreeDesc like", value, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescNotLike(String value) {
            addCriterion("BookCodeThreeDesc not like", value, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescIn(List<String> values) {
            addCriterion("BookCodeThreeDesc in", values, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescNotIn(List<String> values) {
            addCriterion("BookCodeThreeDesc not in", values, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescBetween(String value1, String value2) {
            addCriterion("BookCodeThreeDesc between", value1, value2, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodethreedescNotBetween(String value1, String value2) {
            addCriterion("BookCodeThreeDesc not between", value1, value2, "bookcodethreedesc");
            return (Criteria) this;
        }

        public Criteria andIdxIsNull() {
            addCriterion("Idx is null");
            return (Criteria) this;
        }

        public Criteria andIdxIsNotNull() {
            addCriterion("Idx is not null");
            return (Criteria) this;
        }

        public Criteria andIdxEqualTo(Integer value) {
            addCriterion("Idx =", value, "idx");
            return (Criteria) this;
        }

        public Criteria andIdxNotEqualTo(Integer value) {
            addCriterion("Idx <>", value, "idx");
            return (Criteria) this;
        }

        public Criteria andIdxGreaterThan(Integer value) {
            addCriterion("Idx >", value, "idx");
            return (Criteria) this;
        }

        public Criteria andIdxGreaterThanOrEqualTo(Integer value) {
            addCriterion("Idx >=", value, "idx");
            return (Criteria) this;
        }

        public Criteria andIdxLessThan(Integer value) {
            addCriterion("Idx <", value, "idx");
            return (Criteria) this;
        }

        public Criteria andIdxLessThanOrEqualTo(Integer value) {
            addCriterion("Idx <=", value, "idx");
            return (Criteria) this;
        }

        public Criteria andIdxIn(List<Integer> values) {
            addCriterion("Idx in", values, "idx");
            return (Criteria) this;
        }

        public Criteria andIdxNotIn(List<Integer> values) {
            addCriterion("Idx not in", values, "idx");
            return (Criteria) this;
        }

        public Criteria andIdxBetween(Integer value1, Integer value2) {
            addCriterion("Idx between", value1, value2, "idx");
            return (Criteria) this;
        }

        public Criteria andIdxNotBetween(Integer value1, Integer value2) {
            addCriterion("Idx not between", value1, value2, "idx");
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