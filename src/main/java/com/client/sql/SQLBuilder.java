package com.client.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.client.service.SQLService;


/**
 * SQLBuilder
 * @author peter
 *
 */
public class SQLBuilder<T extends BaseVO> {
	
	private String tableName;
	private List<String> fetchedColumns;
	private List<Where> where;
	private List<String> insertColumns;
	private List<Object> insertValues;
	private List<Set> setList;
	private List<Object> inList;
	
	private Class<T> classInfo;
	private int beginIndex, endIndex;
	private String orderBy;
	private String rangeColumn;
	private String rangeOrderBy;
	private String groupByTarget;
	private String groupByMethod;
	private String groupByColumn;
	
	private Integer nextPKId;
	
	private SQLService service;
	
	public SQLBuilder(SQLService service, Class<T> classInfo) {
		this.service = service;
		this.classInfo = classInfo;
		Table table = classInfo.getDeclaredAnnotation(Table.class);
		this.tableName = table.value();
		this.classInfo = classInfo;
	}
	
	/**
	 * 선택할 칼럼
	 * @param column	선택 칼럼 대상
	 * @return
	 */
	public SQLBuilder<T> columns(String... column) {
		this.fetchedColumns = Arrays.asList(column);
		return this;
	}
	
	/**
	 * where 시작연산 (비교연산은 아직 없음)
	 * @return
	 */
	public SQLBuilder<T> where() {
		this.where = new ArrayList();
		return this;
	}
	/**
	 * 기본 where 시작연산
	 * @param column	테이블 칼럼이름
	 * @param value		value 비교값
	 * @return
	 */
	public SQLBuilder<T> where(String column, Object value) {
		return this.where(column, value, "=");
	}
	/**
	 * where 시작연산
	 * @param column	테이블 칼럼이름
	 * @param value		value 비교값
	 * @param symbol	비교연산 '<', '<=', '>', '>=', '=' 조건을 받는다
	 * @return
	 */
	public SQLBuilder<T> where(String column, Object value, String symbol) {
		this.where = new ArrayList();
		this.where.add(new Where(" and ", column, value, symbol));
		return this;
	}
	/**
	 * where like 비교연산
	 * @param column
	 * @param value
	 * @return
	 */
	public SQLBuilder<T> whereLike(String column, Object value) {
		return this.where(column, value, " like ");
	}
	/**
	 * where is null 연산
	 * @param isNull
	 * @param column
	 * @return
	 */
	public SQLBuilder<T> whereIsNull(boolean isNull, String column) {
		return this.where(column, null, " is "+ (isNull ? "" : "not") +"  null ");
	}
	
	/**
	 * 조회 갯수제한
	 * @Param rangeColumn	갯수 제한의 기준이 되는 칼럼
	 * @param beginIndex	조회 row 시작번호
	 * @param endIndex		조회 row 종료번호
	 * @return
	 */
	public SQLBuilder<T> whereBetween(String rangeColumn, int beginIndex, int endIndex, boolean isAsc) {
		this.where = new ArrayList();
		return this.between(rangeColumn, beginIndex, endIndex, isAsc);
	}
	
	/**
	 * 조회 갯수제한
	 * @Param rangeColumn	갯수 제한의 기준이 되는 칼럼
	 * @param beginIndex	조회 row 시작번호
	 * @param endIndex		조회 row 종료번호
	 * @return
	 */
	public SQLBuilder<T> whereBetween(int beginIndex, int endIndex) {
		return this.whereBetween("id", beginIndex, endIndex, false);
	}
	
	
	/**
	 * where 구문, in을 통해 column을 조회한다
	 * @param column
	 * @param list
	 * @return
	 */
	public SQLBuilder<T> whereIn(String column, Object[] list) {
		this.where = new ArrayList();
		return this.andIn(column,  list);
	}
	
	/**
	 * 날짜를 통해 column을 조회한다
	 * @param column
	 * @param unit 	비교단위 (years: 년, months: 월, days: 날)
	 * @param date
	 * @return
	 */
	public SQLBuilder<T> whereDate(String column, Date date, String unit) {
		this.where = new ArrayList();
		return this.andDate(column, date, unit);
	}
	
	
	
	/**
	 * 기본 where의 and 연산 
	 * @param column	테이블 칼럼이름
	 * @param value		비교대상 값
	 * @param symbol	'<', '<=', '>', '>=', '=' 조건을 받는다
	 * @return
	 */
	public SQLBuilder<T> and(String column, Object value, String symbol) {
		this.where.add(new Where(" and ", column, value, symbol));
		return this;
	}
	
	
	
	/**
	 * 기본 where의 and 연산 비교연산으로는 = 이다
	 * @param column	테이블 칼럼이름
	 * @param value		비교대상 값
	 * @return
	 */
	public SQLBuilder<T> and(String column, Object value) {
		this.where.add(new Where(" and ", column, value, " = "));
		return this;
	}
	/**
	 * where의 and is null 연산
	 * @param column	테이블 칼럼이름
	 * @param isNull	null 할지 not null할지 결정
	 * @return
	 */
	public SQLBuilder<T> andIsNull(boolean isNull, String column) {
		this.where.add(new Where(" and ", column, null, "is "+(isNull ? "" : "not ")+"null"));
		return this;
	}
	/**
	 * and like 연산
	 * @param column
	 * @param value
	 * @return
	 */
	public SQLBuilder<T> andLike(String column, String value) {
		this.where.add(new Where(" and ", column, value, " like "));
		return this;
	}
	
	/**
	 * query in 연산
	 * @param column
	 * @param list
	 * @return
	 */
	public SQLBuilder<T> andIn(String column, Object[] list) {
		this.inList = Arrays.asList(list);
		this.where.add(new Where(" and ", column, "dummy", "in"));
		return this;
	}
	/**
	 * 날짜를 통해 column을 조회한다
	 * @param column
	 * @param unit 	비교단위 (years: 년, months: 월, days: 날)
	 * @param date
	 * @return
	 */
	public SQLBuilder<T> andDate(String column, Date date, String unit) {
		this.where.add(new Where(" and ", column, date, unit));
		return this;
	}
	/**
	 * where의 or 연산
	 * @param column	테이블 칼럼이름
	 * @param value		or
	 * @param symbol	'<', '<=', '>', '>=', '=' 조건을 받는다
	 * @return
	 */
	public SQLBuilder<T> or(String column, Object value, String symbol) {
		this.where.add(new Where(" or ", column, value, symbol));
		return this;
	}
	/**
	 * 기본 where의 or 연산, 비교조건은 = 이다
	 * @param column	테이블 칼럼이름
	 * @param value		or
	 * @return
	 */
	public SQLBuilder<T> or(String column, Object value) {
		this.where.add(new Where(" or ", column, value, " = "));
		return this;
	}
	
	/**
	 * where의 or is null 연산
	 * @param column	테이블 칼럼이름
	 * @param isNull	null할지 not null 할지 결정
	 * @return
	 */
	public SQLBuilder<T> orIsNull(boolean isNull, String column) {
		this.where.add(new Where(" or ", column, null, "is "+(isNull ? "" : "not ")+"null"));
		return this;
	}
	

	/**
	 * or like 연산
	 * @param column 	like 연산대상
	 * @param value  	비교구문 
	 * @return
	 */
	public SQLBuilder<T> orLike(String column, String value) {
		this.where.add(new Where(" or ", column, value, " like "));
		return this;
	}
	
	/**
	 * query in 연산
	 * @param column
	 * @param list
	 * @return
	 */
	public SQLBuilder<T> orIn(String column, Object[] list) {
		this.inList = Arrays.asList(list);
		this.where.add(new Where(" or ", column, "dummy", "in"));
		return this;
	}
	/**
	 * 날짜를 통해 column을 조회한다
	 * @param column
	 * @param unit 	비교단위 (years: 년, months: 월, days: 날)
	 * @param date
	 * @return
	 */
	public SQLBuilder<T> orDate(String column, Date date, String unit) {
		this.where.add(new Where(" or ", column, date, unit));
		return this;
	}
	
	/**
	 * 조회 갯수제한
	 * @Param rangeColumn	갯수 제한의 기준이 되는 칼럼
	 * @param beginIndex	조회 row 시작번호
	 * @param endIndex		조회 row 종료번호
	 * @return
	 */
	public SQLBuilder<T> between(String rangeColumn, int beginIndex, int endIndex, boolean isAsc) {
		this.rangeColumn = "(order by "+rangeColumn+ ( isAsc ? " asc " : " desc )");
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
		return this;
	}
	
	/**
	 * 기본 조회 갯수제한 column = id, 
	 * @param beginIndex	조회 row 시작번호
	 * @param endIndex		조회 row 종료버튼
	 * @return
	 */
	public SQLBuilder<T> between(int beginIndex, int endIndex) {
		return this.between("id", beginIndex, endIndex, false);
	}
	
	/**
	 * order by 를 지정한다  (오름차순, 내림차순)
	 * @param order		asc로 정렬할지 desc로 정렬할지 결정
	 * @param column	정렬대상들을 지정한다 (배열)
	 * @return
	 */
	public SQLBuilder<T> orderby(boolean asc, String... columns) {
		String column = StringUtils.arrayToDelimitedString(columns, ", ");
		this.orderBy = "order by "+column+" " + (asc ? "asc" : "desc");
		return this;
	}
	/**
	 * 오름차순으로 정렬
	 * @param column
	 * @return
	 */
	public SQLBuilder<T> orderby(String... column) {
		return orderby(true, column);
	}
	
	
	
	/**
	 * 데이터베이스 데이터 입력
	 * @param column
	 * @param value
	 * @return
	 */
	public SQLBuilder<T> insert(String column, Object value) {
		if(this.insertColumns == null)  {
			this.insertColumns = new ArrayList<String>();
			this.insertValues = new ArrayList<Object>();
		}
		this.insertColumns.add(column);
		this.insertValues.add(value);
		return this;
	}
	
	
	/**
	 * 데이터베이스 갱신 set 내용 입력
	 * @param column
	 * @param value
	 * @return
	 */
	public SQLBuilder<T> set(String column, Object value) {
		if(this.setList == null) this.setList = new ArrayList();
		this.setList.add(new Set(column, value));
		return this;
	}
	
	public int commitCount() {
		return (int)Math.floor(this.commitMethod("*", "count"));
	}
	
	/**
	 * 조회된 데이터 row 갯수
	 * @return
	 */
	public double commitMethod(String columnName, String method) {
		this.groupByColumn = columnName;
		this.groupByMethod = method;
		return this.service.methodCommit(this);
	}
	/**
	 * group by 연산
	 * @param targetColumn	계산 대상 칼럼
	 * @param groupby		그룹바이 할 칼럼
	 * @param method		계산방식 (sum, count, avg)
	 * @return
	 */
	public Map<String, Object> commitMethodGroupBy(String targetColumn, String groupbyColumn, String method) {
		this.groupByTarget = targetColumn;
		this.groupByColumn = groupbyColumn;
		this.groupByMethod = method;
		return this.service.groupbyCommit(this);
	}
	/**
	 * group by count 연산
	 * @param groupbyColumn  group by 칼럼 이름 
	 * @return
	 */
	public Map<String, Object> commitCountGroupBy(String groupbyColumn) {
		this.groupByTarget = "*";
		this.groupByColumn = groupbyColumn;
		this.groupByMethod = "count";
		return this.service.groupbyCommit(this);
	}
	
	/**
	 * 준비된 builder 내용에 따라 select 실행
	 * @return
	 */
	public List<T> commitSelect() {
		if(this.beginIndex == 0 && this.endIndex == 0)
			return this.service.selectCommit(this);
		else
			return this.service.selectLimitCommit(this);
	}
	
	/**
	 * 준비된 쿼리를 실행시켜 조회되는 데이터가 하나일 경우 이 함수를 실행시킨다.
	 * @return
	 */
	public T commitSelectSingle() {
		List<T> list = null;
		if(this.beginIndex == 0 && this.endIndex == 0)
			list = this.service.selectCommit(this);
		else
			list = this.service.selectLimitCommit(this);
		
		if(list.isEmpty())
			return null;
		
		return list.get(0);
	}
	
	/**
	 * 준비된 builder 내용에 따라 update 실행
	 */
	public void commitUpdate() {
		this.service.updateCommit(this);
	}
	/**
	 * 준비된 builder 내용에 따라 delete 실행
	 */
	public void commitDelete() {
		this.service.deleteCommit(this);
	}
	/**
	 * 준비된 builder 내용에 따라 insert 실행
	 * @return
	 */
	public int commitInsert() {
		return this.service.insertCommit(this);
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 캐시를 위해 builder를 비교하기 위함
	 */
	@Override
	public boolean equals(Object obj) {
		SQLBuilder<T> other = (SQLBuilder<T>)obj;
		boolean indexCondition = other.getBeginIndex() == this.getBeginIndex() && this.getEndIndex() == other.getEndIndex();
		boolean whereCondition = true;
		if(this.where != null) {
			String lang = null;
			for(Where thisWhere : this.where) {
				if(thisWhere.getColumn().equals("LANG_ISO"))
					lang = thisWhere.getValue().toString();
			}
			for(Where otherWhere : other.getWhere()) {
				if(otherWhere.getColumn().equals("LANG_ISO") && otherWhere.getValue().toString().equals(lang))
					break;
			}
			whereCondition = false;
		}
		return indexCondition && whereCondition;
	}
	@Override
	public String toString() {
		return "SQLBuilder [tableName=" + tableName + ", fetchedColumns=" + fetchedColumns + ", where=" + where
				+ ", insertColumns=" + insertColumns + ", insertValues=" + insertValues + ", setList=" + setList
				+ ", inList=" + inList + ", classInfo=" + classInfo + ", beginIndex=" + beginIndex + ", endIndex="
				+ endIndex + ", orderBy=" + orderBy + ", rangeColumn=" + rangeColumn + ", rangeOrderBy=" + rangeOrderBy
				+ ", groupByTarget=" + groupByTarget + ", groupByMethod=" + groupByMethod + ", groupByColumn="
				+ groupByColumn + ", nextPKId=" + nextPKId + ", service=" + service + "]";
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	
	public void setNextPKId(Integer nextPKId) {
		this.nextPKId = nextPKId;
	}
	public Integer getNextPKId() {
		return nextPKId;
	}

	public List<Object> getInList() {
		return inList;
	}

	public Class<T> getClassInfo() {
		return this.classInfo;
	}
	
	
	public String getGroupByMethod() {
		return groupByMethod;
	}

	public String getGroupByColumn() {
		return groupByColumn;
	}

	public String getTableName() {
		return tableName;
	}

	public List<String> getFetchedColumns() {
		return fetchedColumns;
	}

	public List<Where> getWhere() {
		return where;
	}

	public List<String> getInsertColumns() {
		return insertColumns;
	}

	public List<Object> getInsertValues() {
		return insertValues;
	}

	public List<Set> getSetList() {
		return setList;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public String getGroupByTarget() {
		return groupByTarget;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public String getRangeColumn() {
		return rangeColumn;
	}

	public String getRangeOrderBy() {
		return rangeOrderBy;
	}


	public class Where {
		String column, symbol, next;
		Object value;
		
		private Where(String next, String column, Object value, String symbol) {
			this.next = next;
			this.column = column;
			this.value = value;
			this.symbol = symbol;
		}

		public String getColumn() {
			return column;
		}

		public String getSymbol() {
			return symbol;
		}

		public String getNext() {
			return next;
		}

		public Object getValue() {
			return value;
		}
		
	}
	public class Set {
		String column;
		Object value;
		
		private Set(String column, Object value) {
			this.column = column;
			this.value = value;
		}

		public String getColumn() {
			return column;
		}

		public Object getValue() {
			return value;
		}
		
	}
}