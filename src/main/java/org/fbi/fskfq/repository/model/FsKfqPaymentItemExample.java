package org.fbi.fskfq.repository.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FsKfqPaymentItemExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public FsKfqPaymentItemExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
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

        public Criteria andPkidIsNull() {
            addCriterion("PKID is null");
            return (Criteria) this;
        }

        public Criteria andPkidIsNotNull() {
            addCriterion("PKID is not null");
            return (Criteria) this;
        }

        public Criteria andPkidEqualTo(String value) {
            addCriterion("PKID =", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidNotEqualTo(String value) {
            addCriterion("PKID <>", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidGreaterThan(String value) {
            addCriterion("PKID >", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidGreaterThanOrEqualTo(String value) {
            addCriterion("PKID >=", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidLessThan(String value) {
            addCriterion("PKID <", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidLessThanOrEqualTo(String value) {
            addCriterion("PKID <=", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidLike(String value) {
            addCriterion("PKID like", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidNotLike(String value) {
            addCriterion("PKID not like", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidIn(List<String> values) {
            addCriterion("PKID in", values, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidNotIn(List<String> values) {
            addCriterion("PKID not in", values, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidBetween(String value1, String value2) {
            addCriterion("PKID between", value1, value2, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidNotBetween(String value1, String value2) {
            addCriterion("PKID not between", value1, value2, "pkid");
            return (Criteria) this;
        }

        public Criteria andChrIdIsNull() {
            addCriterion("CHR_ID is null");
            return (Criteria) this;
        }

        public Criteria andChrIdIsNotNull() {
            addCriterion("CHR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andChrIdEqualTo(String value) {
            addCriterion("CHR_ID =", value, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdNotEqualTo(String value) {
            addCriterion("CHR_ID <>", value, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdGreaterThan(String value) {
            addCriterion("CHR_ID >", value, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdGreaterThanOrEqualTo(String value) {
            addCriterion("CHR_ID >=", value, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdLessThan(String value) {
            addCriterion("CHR_ID <", value, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdLessThanOrEqualTo(String value) {
            addCriterion("CHR_ID <=", value, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdLike(String value) {
            addCriterion("CHR_ID like", value, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdNotLike(String value) {
            addCriterion("CHR_ID not like", value, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdIn(List<String> values) {
            addCriterion("CHR_ID in", values, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdNotIn(List<String> values) {
            addCriterion("CHR_ID not in", values, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdBetween(String value1, String value2) {
            addCriterion("CHR_ID between", value1, value2, "chrId");
            return (Criteria) this;
        }

        public Criteria andChrIdNotBetween(String value1, String value2) {
            addCriterion("CHR_ID not between", value1, value2, "chrId");
            return (Criteria) this;
        }

        public Criteria andMainIdIsNull() {
            addCriterion("MAIN_ID is null");
            return (Criteria) this;
        }

        public Criteria andMainIdIsNotNull() {
            addCriterion("MAIN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMainIdEqualTo(String value) {
            addCriterion("MAIN_ID =", value, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdNotEqualTo(String value) {
            addCriterion("MAIN_ID <>", value, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdGreaterThan(String value) {
            addCriterion("MAIN_ID >", value, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdGreaterThanOrEqualTo(String value) {
            addCriterion("MAIN_ID >=", value, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdLessThan(String value) {
            addCriterion("MAIN_ID <", value, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdLessThanOrEqualTo(String value) {
            addCriterion("MAIN_ID <=", value, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdLike(String value) {
            addCriterion("MAIN_ID like", value, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdNotLike(String value) {
            addCriterion("MAIN_ID not like", value, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdIn(List<String> values) {
            addCriterion("MAIN_ID in", values, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdNotIn(List<String> values) {
            addCriterion("MAIN_ID not in", values, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdBetween(String value1, String value2) {
            addCriterion("MAIN_ID between", value1, value2, "mainId");
            return (Criteria) this;
        }

        public Criteria andMainIdNotBetween(String value1, String value2) {
            addCriterion("MAIN_ID not between", value1, value2, "mainId");
            return (Criteria) this;
        }

        public Criteria andInBisCodeIsNull() {
            addCriterion("IN_BIS_CODE is null");
            return (Criteria) this;
        }

        public Criteria andInBisCodeIsNotNull() {
            addCriterion("IN_BIS_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andInBisCodeEqualTo(String value) {
            addCriterion("IN_BIS_CODE =", value, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeNotEqualTo(String value) {
            addCriterion("IN_BIS_CODE <>", value, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeGreaterThan(String value) {
            addCriterion("IN_BIS_CODE >", value, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeGreaterThanOrEqualTo(String value) {
            addCriterion("IN_BIS_CODE >=", value, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeLessThan(String value) {
            addCriterion("IN_BIS_CODE <", value, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeLessThanOrEqualTo(String value) {
            addCriterion("IN_BIS_CODE <=", value, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeLike(String value) {
            addCriterion("IN_BIS_CODE like", value, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeNotLike(String value) {
            addCriterion("IN_BIS_CODE not like", value, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeIn(List<String> values) {
            addCriterion("IN_BIS_CODE in", values, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeNotIn(List<String> values) {
            addCriterion("IN_BIS_CODE not in", values, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeBetween(String value1, String value2) {
            addCriterion("IN_BIS_CODE between", value1, value2, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisCodeNotBetween(String value1, String value2) {
            addCriterion("IN_BIS_CODE not between", value1, value2, "inBisCode");
            return (Criteria) this;
        }

        public Criteria andInBisNameIsNull() {
            addCriterion("IN_BIS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andInBisNameIsNotNull() {
            addCriterion("IN_BIS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andInBisNameEqualTo(String value) {
            addCriterion("IN_BIS_NAME =", value, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameNotEqualTo(String value) {
            addCriterion("IN_BIS_NAME <>", value, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameGreaterThan(String value) {
            addCriterion("IN_BIS_NAME >", value, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameGreaterThanOrEqualTo(String value) {
            addCriterion("IN_BIS_NAME >=", value, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameLessThan(String value) {
            addCriterion("IN_BIS_NAME <", value, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameLessThanOrEqualTo(String value) {
            addCriterion("IN_BIS_NAME <=", value, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameLike(String value) {
            addCriterion("IN_BIS_NAME like", value, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameNotLike(String value) {
            addCriterion("IN_BIS_NAME not like", value, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameIn(List<String> values) {
            addCriterion("IN_BIS_NAME in", values, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameNotIn(List<String> values) {
            addCriterion("IN_BIS_NAME not in", values, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameBetween(String value1, String value2) {
            addCriterion("IN_BIS_NAME between", value1, value2, "inBisName");
            return (Criteria) this;
        }

        public Criteria andInBisNameNotBetween(String value1, String value2) {
            addCriterion("IN_BIS_NAME not between", value1, value2, "inBisName");
            return (Criteria) this;
        }

        public Criteria andMeasureIsNull() {
            addCriterion("MEASURE is null");
            return (Criteria) this;
        }

        public Criteria andMeasureIsNotNull() {
            addCriterion("MEASURE is not null");
            return (Criteria) this;
        }

        public Criteria andMeasureEqualTo(String value) {
            addCriterion("MEASURE =", value, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureNotEqualTo(String value) {
            addCriterion("MEASURE <>", value, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureGreaterThan(String value) {
            addCriterion("MEASURE >", value, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureGreaterThanOrEqualTo(String value) {
            addCriterion("MEASURE >=", value, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureLessThan(String value) {
            addCriterion("MEASURE <", value, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureLessThanOrEqualTo(String value) {
            addCriterion("MEASURE <=", value, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureLike(String value) {
            addCriterion("MEASURE like", value, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureNotLike(String value) {
            addCriterion("MEASURE not like", value, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureIn(List<String> values) {
            addCriterion("MEASURE in", values, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureNotIn(List<String> values) {
            addCriterion("MEASURE not in", values, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureBetween(String value1, String value2) {
            addCriterion("MEASURE between", value1, value2, "measure");
            return (Criteria) this;
        }

        public Criteria andMeasureNotBetween(String value1, String value2) {
            addCriterion("MEASURE not between", value1, value2, "measure");
            return (Criteria) this;
        }

        public Criteria andChargenumIsNull() {
            addCriterion("CHARGENUM is null");
            return (Criteria) this;
        }

        public Criteria andChargenumIsNotNull() {
            addCriterion("CHARGENUM is not null");
            return (Criteria) this;
        }

        public Criteria andChargenumEqualTo(String value) {
            addCriterion("CHARGENUM =", value, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumNotEqualTo(String value) {
            addCriterion("CHARGENUM <>", value, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumGreaterThan(String value) {
            addCriterion("CHARGENUM >", value, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumGreaterThanOrEqualTo(String value) {
            addCriterion("CHARGENUM >=", value, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumLessThan(String value) {
            addCriterion("CHARGENUM <", value, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumLessThanOrEqualTo(String value) {
            addCriterion("CHARGENUM <=", value, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumLike(String value) {
            addCriterion("CHARGENUM like", value, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumNotLike(String value) {
            addCriterion("CHARGENUM not like", value, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumIn(List<String> values) {
            addCriterion("CHARGENUM in", values, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumNotIn(List<String> values) {
            addCriterion("CHARGENUM not in", values, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumBetween(String value1, String value2) {
            addCriterion("CHARGENUM between", value1, value2, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargenumNotBetween(String value1, String value2) {
            addCriterion("CHARGENUM not between", value1, value2, "chargenum");
            return (Criteria) this;
        }

        public Criteria andChargestandardIsNull() {
            addCriterion("CHARGESTANDARD is null");
            return (Criteria) this;
        }

        public Criteria andChargestandardIsNotNull() {
            addCriterion("CHARGESTANDARD is not null");
            return (Criteria) this;
        }

        public Criteria andChargestandardEqualTo(String value) {
            addCriterion("CHARGESTANDARD =", value, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardNotEqualTo(String value) {
            addCriterion("CHARGESTANDARD <>", value, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardGreaterThan(String value) {
            addCriterion("CHARGESTANDARD >", value, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardGreaterThanOrEqualTo(String value) {
            addCriterion("CHARGESTANDARD >=", value, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardLessThan(String value) {
            addCriterion("CHARGESTANDARD <", value, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardLessThanOrEqualTo(String value) {
            addCriterion("CHARGESTANDARD <=", value, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardLike(String value) {
            addCriterion("CHARGESTANDARD like", value, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardNotLike(String value) {
            addCriterion("CHARGESTANDARD not like", value, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardIn(List<String> values) {
            addCriterion("CHARGESTANDARD in", values, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardNotIn(List<String> values) {
            addCriterion("CHARGESTANDARD not in", values, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardBetween(String value1, String value2) {
            addCriterion("CHARGESTANDARD between", value1, value2, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargestandardNotBetween(String value1, String value2) {
            addCriterion("CHARGESTANDARD not between", value1, value2, "chargestandard");
            return (Criteria) this;
        }

        public Criteria andChargemoneyIsNull() {
            addCriterion("CHARGEMONEY is null");
            return (Criteria) this;
        }

        public Criteria andChargemoneyIsNotNull() {
            addCriterion("CHARGEMONEY is not null");
            return (Criteria) this;
        }

        public Criteria andChargemoneyEqualTo(BigDecimal value) {
            addCriterion("CHARGEMONEY =", value, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andChargemoneyNotEqualTo(BigDecimal value) {
            addCriterion("CHARGEMONEY <>", value, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andChargemoneyGreaterThan(BigDecimal value) {
            addCriterion("CHARGEMONEY >", value, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andChargemoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CHARGEMONEY >=", value, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andChargemoneyLessThan(BigDecimal value) {
            addCriterion("CHARGEMONEY <", value, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andChargemoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CHARGEMONEY <=", value, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andChargemoneyIn(List<BigDecimal> values) {
            addCriterion("CHARGEMONEY in", values, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andChargemoneyNotIn(List<BigDecimal> values) {
            addCriterion("CHARGEMONEY not in", values, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andChargemoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHARGEMONEY between", value1, value2, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andChargemoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHARGEMONEY not between", value1, value2, "chargemoney");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeIsNull() {
            addCriterion("ITEM_CHKCODE is null");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeIsNotNull() {
            addCriterion("ITEM_CHKCODE is not null");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeEqualTo(String value) {
            addCriterion("ITEM_CHKCODE =", value, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeNotEqualTo(String value) {
            addCriterion("ITEM_CHKCODE <>", value, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeGreaterThan(String value) {
            addCriterion("ITEM_CHKCODE >", value, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ITEM_CHKCODE >=", value, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeLessThan(String value) {
            addCriterion("ITEM_CHKCODE <", value, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeLessThanOrEqualTo(String value) {
            addCriterion("ITEM_CHKCODE <=", value, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeLike(String value) {
            addCriterion("ITEM_CHKCODE like", value, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeNotLike(String value) {
            addCriterion("ITEM_CHKCODE not like", value, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeIn(List<String> values) {
            addCriterion("ITEM_CHKCODE in", values, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeNotIn(List<String> values) {
            addCriterion("ITEM_CHKCODE not in", values, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeBetween(String value1, String value2) {
            addCriterion("ITEM_CHKCODE between", value1, value2, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andItemChkcodeNotBetween(String value1, String value2) {
            addCriterion("ITEM_CHKCODE not between", value1, value2, "itemChkcode");
            return (Criteria) this;
        }

        public Criteria andMainPkidIsNull() {
            addCriterion("MAIN_PKID is null");
            return (Criteria) this;
        }

        public Criteria andMainPkidIsNotNull() {
            addCriterion("MAIN_PKID is not null");
            return (Criteria) this;
        }

        public Criteria andMainPkidEqualTo(String value) {
            addCriterion("MAIN_PKID =", value, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidNotEqualTo(String value) {
            addCriterion("MAIN_PKID <>", value, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidGreaterThan(String value) {
            addCriterion("MAIN_PKID >", value, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidGreaterThanOrEqualTo(String value) {
            addCriterion("MAIN_PKID >=", value, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidLessThan(String value) {
            addCriterion("MAIN_PKID <", value, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidLessThanOrEqualTo(String value) {
            addCriterion("MAIN_PKID <=", value, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidLike(String value) {
            addCriterion("MAIN_PKID like", value, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidNotLike(String value) {
            addCriterion("MAIN_PKID not like", value, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidIn(List<String> values) {
            addCriterion("MAIN_PKID in", values, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidNotIn(List<String> values) {
            addCriterion("MAIN_PKID not in", values, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidBetween(String value1, String value2) {
            addCriterion("MAIN_PKID between", value1, value2, "mainPkid");
            return (Criteria) this;
        }

        public Criteria andMainPkidNotBetween(String value1, String value2) {
            addCriterion("MAIN_PKID not between", value1, value2, "mainPkid");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated do_not_delete_during_merge Mon Jan 06 16:49:52 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.FS_KFQ_PAYMENT_ITEM
     *
     * @mbggenerated Mon Jan 06 16:49:52 CST 2014
     */
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