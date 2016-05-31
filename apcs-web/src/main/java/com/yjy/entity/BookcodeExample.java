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

        public Criteria andPlatformroleIsNull() {
            addCriterion("PlatFormRole is null");
            return (Criteria) this;
        }

        public Criteria andPlatformroleIsNotNull() {
            addCriterion("PlatFormRole is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformroleEqualTo(Integer value) {
            addCriterion("PlatFormRole =", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleNotEqualTo(Integer value) {
            addCriterion("PlatFormRole <>", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleGreaterThan(Integer value) {
            addCriterion("PlatFormRole >", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleGreaterThanOrEqualTo(Integer value) {
            addCriterion("PlatFormRole >=", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleLessThan(Integer value) {
            addCriterion("PlatFormRole <", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleLessThanOrEqualTo(Integer value) {
            addCriterion("PlatFormRole <=", value, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleIn(List<Integer> values) {
            addCriterion("PlatFormRole in", values, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleNotIn(List<Integer> values) {
            addCriterion("PlatFormRole not in", values, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleBetween(Integer value1, Integer value2) {
            addCriterion("PlatFormRole between", value1, value2, "platformrole");
            return (Criteria) this;
        }

        public Criteria andPlatformroleNotBetween(Integer value1, Integer value2) {
            addCriterion("PlatFormRole not between", value1, value2, "platformrole");
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

        public Criteria andPnameIsNull() {
            addCriterion("PName is null");
            return (Criteria) this;
        }

        public Criteria andPnameIsNotNull() {
            addCriterion("PName is not null");
            return (Criteria) this;
        }

        public Criteria andPnameEqualTo(String value) {
            addCriterion("PName =", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotEqualTo(String value) {
            addCriterion("PName <>", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameGreaterThan(String value) {
            addCriterion("PName >", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameGreaterThanOrEqualTo(String value) {
            addCriterion("PName >=", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameLessThan(String value) {
            addCriterion("PName <", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameLessThanOrEqualTo(String value) {
            addCriterion("PName <=", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameLike(String value) {
            addCriterion("PName like", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotLike(String value) {
            addCriterion("PName not like", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameIn(List<String> values) {
            addCriterion("PName in", values, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotIn(List<String> values) {
            addCriterion("PName not in", values, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameBetween(String value1, String value2) {
            addCriterion("PName between", value1, value2, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotBetween(String value1, String value2) {
            addCriterion("PName not between", value1, value2, "pname");
            return (Criteria) this;
        }

        public Criteria andEnameIsNull() {
            addCriterion("EName is null");
            return (Criteria) this;
        }

        public Criteria andEnameIsNotNull() {
            addCriterion("EName is not null");
            return (Criteria) this;
        }

        public Criteria andEnameEqualTo(String value) {
            addCriterion("EName =", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameNotEqualTo(String value) {
            addCriterion("EName <>", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameGreaterThan(String value) {
            addCriterion("EName >", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameGreaterThanOrEqualTo(String value) {
            addCriterion("EName >=", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameLessThan(String value) {
            addCriterion("EName <", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameLessThanOrEqualTo(String value) {
            addCriterion("EName <=", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameLike(String value) {
            addCriterion("EName like", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameNotLike(String value) {
            addCriterion("EName not like", value, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameIn(List<String> values) {
            addCriterion("EName in", values, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameNotIn(List<String> values) {
            addCriterion("EName not in", values, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameBetween(String value1, String value2) {
            addCriterion("EName between", value1, value2, "ename");
            return (Criteria) this;
        }

        public Criteria andEnameNotBetween(String value1, String value2) {
            addCriterion("EName not between", value1, value2, "ename");
            return (Criteria) this;
        }

        public Criteria andAnameIsNull() {
            addCriterion("AName is null");
            return (Criteria) this;
        }

        public Criteria andAnameIsNotNull() {
            addCriterion("AName is not null");
            return (Criteria) this;
        }

        public Criteria andAnameEqualTo(String value) {
            addCriterion("AName =", value, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameNotEqualTo(String value) {
            addCriterion("AName <>", value, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameGreaterThan(String value) {
            addCriterion("AName >", value, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameGreaterThanOrEqualTo(String value) {
            addCriterion("AName >=", value, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameLessThan(String value) {
            addCriterion("AName <", value, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameLessThanOrEqualTo(String value) {
            addCriterion("AName <=", value, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameLike(String value) {
            addCriterion("AName like", value, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameNotLike(String value) {
            addCriterion("AName not like", value, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameIn(List<String> values) {
            addCriterion("AName in", values, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameNotIn(List<String> values) {
            addCriterion("AName not in", values, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameBetween(String value1, String value2) {
            addCriterion("AName between", value1, value2, "aname");
            return (Criteria) this;
        }

        public Criteria andAnameNotBetween(String value1, String value2) {
            addCriterion("AName not between", value1, value2, "aname");
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