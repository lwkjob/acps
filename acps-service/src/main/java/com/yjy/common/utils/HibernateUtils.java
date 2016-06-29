 
package com.yjy.common.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description
 * @author  zhaoyuan
 * @version 1.0,2010-9-15
 */
public class HibernateUtils {

	private static final Pattern FROM_PATTERN = Pattern
			.compile("(^|\\s)from\\s");
	private static final Pattern GROUPBY_PATTERN = Pattern
			.compile("\\sgroup\\s+by\\s");
	private static final Pattern ORDERBY_PATTERN = Pattern
			.compile("\\sorder\\s+by\\s");
	private static final Pattern HAVING_PATTERN = Pattern
			.compile("\\shaving(\\s|\\()");

    //子查询模式
    private static final Pattern SUB_SELECT_PATTERN = Pattern
            .compile("\\((\\s)*select\\s");
	 
	/**
	 * 判断hql中是否存在 having 子句
	 * @author xujianguo
	 * @return
	 */
	public static final boolean isExistHavingHql(String hql) {
		return HAVING_PATTERN.matcher(hql).find();
	}

    /**
     * 思路：
     * 找到外层的Select 和 与他匹配的 from
     * 将他们之间的内容替换为 " count(*) "
     * @param hql
     * @return
     */
    public static final String countHql(String hql) {

        hql = hql.trim();
        String tempHql = hql.toLowerCase();
        int hqlLength = tempHql.length();
        Matcher fromMatcher = FROM_PATTERN.matcher(tempHql);

        int fromIndex = -1;
        /*
            * 2014年10月13日19:02:14 zhaoyuan 修改
            * 修改原因：有子查询的时候，去最后一个from会出错
            * */
        List<SubQueryRang> subQuerys = findSubQuerys(hql);
        while(fromMatcher.find()) {
            int tmp = fromMatcher.start();
            if(!isInSubQuery(tmp,subQuerys)){
                fromIndex = tmp;
            }
        }
        if(fromIndex==-1) return null;

        int selectIndex = tempHql.indexOf("select");

        return hql.substring(0,selectIndex) + " SELECT COUNT(*) " + hql.substring(fromIndex);

    }
	
//	public static final String countHql(String hql) {
//
//		hql = hql.trim();
//		String tempHql = hql.toLowerCase();
//		int hqlLength = tempHql.length();
//		Matcher fromMatcher = FROM_PATTERN.matcher(tempHql);
//
//		int fromIndex = -1;
//        /*
//            * 2014年10月13日19:02:14 zhaoyuan 修改
//            * 修改原因：有子查询的时候，去最后一个from会出错
//            * */
//		if(fromMatcher.find()) {
//			fromIndex = fromMatcher.end();
//		}
//		if(fromIndex==-1) return null;
//
//		Matcher groupByMathcer = GROUPBY_PATTERN.matcher(tempHql);
//		int groupByIndex = -1;
//		int groupByEnd = -1;
//		if (groupByMathcer.find()) {
//			groupByIndex = groupByMathcer.start();
//			groupByEnd = groupByMathcer.end();
//		}
//		Matcher orderByMathcer = ORDERBY_PATTERN.matcher(tempHql);
//		int orderByIndex = orderByMathcer.find() ? orderByMathcer.start()
//				: -1;
//		Matcher havingMathcer = HAVING_PATTERN.matcher(tempHql);
//		int havingIndex = havingMathcer.find() ? havingMathcer.start() : -1;
//
//		int endIndex = groupByIndex;
//		if (orderByIndex != -1) {
//			if (endIndex != -1)
//				endIndex = endIndex < orderByIndex ? endIndex
//						: orderByIndex;
//			else
//				endIndex = orderByIndex;
//		}
//		if (havingIndex != -1) {
//			if (endIndex != -1)
//				endIndex = endIndex < havingIndex ? endIndex : havingIndex;
//			else
//				endIndex = havingIndex;
//		}
//		endIndex = endIndex == -1 ? hqlLength : endIndex;
//		int groupByFieldIndex = -1;
//		if (groupByIndex != -1) {
//			if (orderByIndex > groupByIndex) {
//				groupByFieldIndex = orderByIndex;
//			}
//			if (havingIndex > groupByIndex) {
//				if (groupByFieldIndex != -1)
//					groupByFieldIndex = groupByFieldIndex < havingIndex ? groupByFieldIndex
//							: havingIndex;
//				else
//					groupByFieldIndex = havingIndex;
//			}
//			groupByFieldIndex = groupByFieldIndex == -1 ? hqlLength
//					: groupByFieldIndex;
//		}
//		String selectHql = groupByIndex == -1 ? "select count(*)"
//				: "select count(distinct "
//						+ hql.substring(groupByEnd, groupByFieldIndex)
//						+ ")";
//		if(havingIndex==-1)
//			return selectHql + " from "
//					+ hql.substring(fromIndex, endIndex);
//		else {
//			return selectHql + " from "
//			+ hql.substring(fromIndex, orderByIndex==-1?hqlLength:orderByIndex);
//		}
//	}
	/**
	 * 求和hql
	 * @author zhaoyuan
	 * @param hql
	 * @param sumName
	 * @return
	 */
	public static final String sumHql(String hql,String sumName) {
		String countHql = countHql(hql);
		return countHql.replaceAll("count\\([^\\)]*\\)", "sum("+sumName+")");
	}

    private static List<SubQueryRang> findSubQuerys(String hql){


        hql = hql.trim();
        String tempHql = hql.toLowerCase();
        int hqlLength = tempHql.length();
        Matcher subMatcher = SUB_SELECT_PATTERN.matcher(tempHql);
        List<SubQueryRang> subQuery = new LinkedList<SubQueryRang>();
        int fromIndex = -1;
        /*
            * 2014年10月13日19:02:14 zhaoyuan 修改
            * 修改原因：有子查询的时候，去最后一个from会出错
            * */
        while(subMatcher.find()) {
            subQuery.add(new SubQueryRang(subMatcher.start()));
        }

        Stack<Integer> qst = new Stack<Integer>();

        for (SubQueryRang subQueryRang : subQuery) {
            qst.push(subQueryRang.start);
            for(int i = subQueryRang.start + 1;i < hqlLength;i++){
                if('(' == (hql.charAt(i))){
                    qst.push(i);
                }else{
                    if(')' == (hql.charAt(i))){
                        qst.pop();
                        if(qst.empty()){
                            subQueryRang.end = i;
                            break;
                        }

                    }
                }
            }
        }
        return subQuery;
    }

    private static boolean isInSubQuery(int index,List<SubQueryRang> subQueryRangs){
        if(subQueryRangs == null || subQueryRangs.isEmpty()) return false;
        for (SubQueryRang subQueryRang : subQueryRangs) {
             if(index >= subQueryRang.start && index <= subQueryRang.end){
                 return true;
             }
        }
        return false;
    }
	

//	/**
//	 * 根据查询参数 设置query对象的查询参数
//	 * @author zhaoyuan
//	 * @param query
//	 * @param paramMap
//	 */
//	public static final void paramQuery(Query query, Map<String,Object> paramMap) {
//		if (paramMap != null) {
//			String[] params = query.getNamedParameters();
//			for (int i = 0; i < params.length; i++) {
//				String param = params[i];
//				Object value = paramMap.get(param);
//				if(value instanceof Collection)
//					query.setParameterList(param, (Collection<?>)value);
//				else if(value instanceof Object[])
//					query.setParameterList(param, (Object[])value);
//				else
//					query.setParameter(param, value);
//			}
//		}
//	}
	
//	/**
//	 * 根据查询参数 设置query对象的查询参数
//	 * @author zhaoyuan
//	 * @param query
//	 * @param properties
//	 */
//	public static final void paramQuery(Query query, Object[] properties) {
//		if (properties != null) {
//			for(int i = 0;i<properties.length;i++) {
//				query.setParameter(i, properties[i]);
//			}
//		}
//	}
/*	
	public static void main(String[] args) {
		String hql =   
				 "select merchandise_id,max(update_date) update_date,"+
						" (select price from tb_price where merchandise_id=a.merchandise_id and price_type_id=1) as P1,"+
						 "(select price from tb_price where merchandise_id=a.merchandise_id and price_type_id=2) as P2,"+
						" (select price from tb_price where merchandise_id=a.merchandise_id and price_type_id=3) as P3,"+
						" (select price from tb_price where merchandise_id=a.merchandise_id and price_type_id=4) as P4,"+
						" (select price from tb_price where merchandise_id=a.merchandise_id and price_type_id=5) as P5"+
						"  from  tb_price a group by merchandise_id";
		
		hql ="select *  from  from User";
		Matcher fromMatcher = FROM_PATTERN.matcher(hql);
		System.out.println(fromMatcher.regionStart());
		while(fromMatcher.find()) {
			System.out.println(fromMatcher.start()+" : "+fromMatcher.end());
		}
		
		System.out.println(hql);
		System.out.println(countHql(hql));
	}*/

    /**
     * 子查询位置 范围区间
     */
    private static class SubQueryRang {
        int start;

        int end = -1;

        private SubQueryRang(int start) {
            this.start = start;
        }

        private SubQueryRang(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}