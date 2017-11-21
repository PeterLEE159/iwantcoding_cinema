package com.client.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.client.sql.BaseVO;
import com.client.sql.SQLBuilder;
import com.client.sql.VOFactory;

public abstract class SQLService {
	
	
	protected VOFactory factory;
	
	{
		factory = VOFactory.getFactory();
	}
	
	/**
	 * 데이터를 조작할 데이터베이스 테이블을 지정한다
	 * @param 	classInfo		테이블 정보 (테이블 이름, 테이블 칼럼)
	 * @return	SQLBuilder	
	 */
	public <T extends BaseVO> SQLBuilder<T> table(Class<T> classInfo) {
		return new SQLBuilder<T>(this, classInfo);
	}
	
	/**
	 * 범위로 조회
	 * @param builder
	 * @return
	 */
	public abstract <T extends BaseVO> List<T> selectLimitCommit(SQLBuilder<T> builder);
	
	
	/**
	 * sum, avg, count() 로 값을 연산한다
	 * @param builder
	 * @return
	 */
	public abstract double methodCommit(SQLBuilder builder);
	
	/**
	 * group commit
	 * @param builder
	 * @return
	 */
	public abstract Map<String, Object> groupbyCommit(SQLBuilder builder);
	

	/**
	 * 기본 select
	 * @param builder
	 * @return
	 */
	public abstract <T extends BaseVO> List<T> selectCommit(SQLBuilder<T> builder);
	/**
	 * 테이블 데이터 갱신
	 * @param builder
	 */
	public abstract <T extends BaseVO> void updateCommit(SQLBuilder<T> builder);
	/**
	 * 테이블 데이터 삭제
	 * @param builder
	 */
	public abstract <T extends BaseVO> void deleteCommit(SQLBuilder<T> builder);
	
	/**
	 * 테이블 데이터 삽입
	 * @param builder
	 */
	public abstract <T extends BaseVO> int insertCommit(SQLBuilder<T> builder);
	
	/**
	 * mapper에서 list로 받은 데이터를 타겟 VO로 전환
	 * @param builder
	 * @param fetchedData
	 * @return
	 */
	protected abstract <T extends BaseVO> List<T> convertListToTarget(SQLBuilder<T> builder, List<Map<String, Object>> fetchedData);
	

	
	

	/**
	 * 아이디를 통해 객체 조회
	 * @param builder
	 */
	public abstract <T extends BaseVO> T directSelect(Class<T> classInfo, int id);
	
	
	
	/**
	 * 객체를 통해 데이터 삽입
	 * @param builder
	 */
	public abstract <T extends BaseVO> int directInsert(T vo);
	/**
	 * 객체를 통해 데이터 갱신
	 * @param builder
	 */
	public abstract <T extends BaseVO> void directUpdate(T vo);
	
	/**
	 * 아이디를 통해 객체삭제
	 * @param classInfo
	 * @param id
	 */
	public abstract <T extends BaseVO> void directDelete(Class<T> classInfo, int id);
	
	/**
	 * 객체를 통해 데이터 삭제
	 * @param vo
	 */
	public abstract <T extends BaseVO> void directDelete(T vo);
	
	/**
	 * bigdecimal 이 정수인지 구분
	 * @param bd
	 * @return
	 */
	protected boolean isIntegerValue(BigDecimal bd) {
	  return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
	}
}
