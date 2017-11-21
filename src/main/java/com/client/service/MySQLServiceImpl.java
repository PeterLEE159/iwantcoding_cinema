package com.client.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.client.mapper.SQLMapper;
import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.SQLBuilder;
import com.client.sql.Table;


/**
 * SQLService를 통해 데이터베이스 접근
 * @author gawon
 */
@Service
public class MySQLServiceImpl extends SQLService {
	
	
	@Autowired
	protected SQLMapper sqlMapper;
	/**
	 * 아이디를 통해 객체 조회
	 * @param builder
	 */
	public <T extends BaseVO> T directSelect(Class<T> classInfo, int id) {
		try {
			
			SQLBuilder<T> builder = this.table(classInfo).where("ID", id);
			List<Map<String, Object>> resultData = this.sqlMapper.select(builder);
			if(resultData == null || resultData.size() == 0 ) return null;
			Map<String, Object> fetchedData = resultData.get(0);
			
			T data = (T) factory.getInstance(classInfo.getName());
			Field[] fields = classInfo.getDeclaredFields();
			
			for(Field field : fields) {
				field.setAccessible(true);
				Column column = field.getDeclaredAnnotation(Column.class);
				if(column != null) {
					String declaredColumn = column.value();
					
					Object value = fetchedData.get(declaredColumn);
					if(value != null) {
						if(value instanceof BigDecimal) {
							BigDecimal bg = ((BigDecimal) value) ;
							if(this.isIntegerValue(bg)) field.set(data, bg.intValue());
							else field.set(data, bg.doubleValue());
						}
						else
							field.set(data, (value));
						
					}
				}
				field.setAccessible(false);
			}
			return data;
			
			
		} catch (Exception ex ) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	
	
	/**
	 * 객체를 통해 데이터 삽입
	 * @param builder
	 */
	public <T extends BaseVO> int directInsert(T vo) {
		
		try {
			Class<T> classInfo = (Class<T>) vo.getClass();
			SQLBuilder<T> builder = this.table(classInfo);
			
			Field[] fields = classInfo.getDeclaredFields();
			for(Field field : fields) {
				if(field != null) {
					field.setAccessible(true);
					Column aColumn = field.getDeclaredAnnotation(Column.class);
					Object data = field.get(vo);
					if(aColumn != null && data != null) {
						String column = aColumn.value(); 
						if(!column.equals("ID")) builder.insert(column, data);
						field.setAccessible(false);
					}
				}
			}
			
			return this.insertCommit(builder);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	/**
	 * 객체를 통해 데이터 갱신
	 * @param builder
	 */
	public <T extends BaseVO> void directUpdate(T vo) {
		try {
			Class<T> classInfo = (Class<T>) vo.getClass();
			SQLBuilder<T> builder = this.table(classInfo);
			
			Field[] fields = classInfo.getDeclaredFields();
			
			for(Field field : fields) {
				field.setAccessible(true);
				Column aColumn = field.getDeclaredAnnotation(Column.class);
				if(aColumn != null) {
					String column = aColumn.value();
					Object value = field.get(vo);
					if(!column.equals("ID")) {
						if(value != null) builder.set(column, value);
					}
					else builder.where(column, value);
					field.setAccessible(false);
				}
			}
			
			
			this.updateCommit(builder);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 아이디를 통해 객체삭제
	 * @param classInfo
	 * @param id
	 */
	public <T extends BaseVO> void directDelete(Class<T> classInfo, int id) {
		Table table = classInfo.getDeclaredAnnotation(Table.class);
		SQLBuilder<T> builder = this.table(classInfo);
		builder.where("ID", id);
		this.deleteCommit(builder);
	}
	
	/**
	 * 객체를 통해 데이터 삭제
	 * @param vo
	 */
	public <T extends BaseVO> void directDelete(T vo) {
		try {
			Class<T> classInfo = (Class<T>) vo.getClass();
			Field[] fields = classInfo.getDeclaredFields();
			for(Field field : fields) {
				field.setAccessible(true);
				Column column = field.getAnnotation(Column.class);
				if(column != null && "ID".equals(column.value())) {
					Object value;
					value = field.get(vo);
					
					if(value instanceof Integer)
						this.directDelete(classInfo, Integer.parseInt(value.toString()));
				}
				field.setAccessible(false);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 범위로 조회
	 * @param builder
	 * @return
	 */
	public <T extends BaseVO> List<T> selectLimitCommit(SQLBuilder<T> builder) {
		builder.setBeginIndex(builder.getBeginIndex() - 1);
		List<Map<String, Object>> fetchedData = this.sqlMapper.selectLimit(builder);
		return this.convertListToTarget(builder, fetchedData);
	}
	
	
	/**
	 * sum, avg, count() 로 값을 연산한다
	 * @param builder
	 * @return
	 */
	public double methodCommit(SQLBuilder builder) {
		Object value =  this.sqlMapper.method(builder);
//		BigDecimal bd = (BigDecimal)value;
//		if(bd == null) return 0;
//		if(this.isIntegerValue(bd))
//			return (double)(bd.intValue());
		if(value == null)
			return 0;
		
		return Double.parseDouble(value.toString());
	}
	
	/**
	 * group commit
	 * @param builder
	 * @return
	 */
	public Map<String, Object> groupbyCommit(SQLBuilder builder) {
		List<Map<String, Object>> fetchedData = this.sqlMapper.groupby(builder);
		Map<String, Object> data = new HashMap();
		String dataKey = builder.getGroupByMethod().toUpperCase();
		String columnKey = builder.getGroupByColumn().toUpperCase();
		
		for(Map<String, Object> eachData : fetchedData) {
			BigDecimal bd = (BigDecimal)eachData.get(dataKey);
			Object key = eachData.get(columnKey);
			if(this.isIntegerValue(bd))
				data.put(key == null ? "" : key.toString(), bd.intValue());
			else
				data.put(key == null ? "" : key.toString(), bd.doubleValue());
		}
		return data;
	}
	

	/**
	 * 기본 select
	 * @param builder
	 * @return
	 */
	public <T extends BaseVO> List<T> selectCommit(SQLBuilder<T> builder)  {
		List<Map<String, Object>> fetchedData = this.sqlMapper.select(builder);
		return this.convertListToTarget(builder, fetchedData);
	}
	/**
	 * 테이블 데이터 갱신
	 * @param builder
	 */
	public <T extends BaseVO> void updateCommit(SQLBuilder<T> builder) {
		this.sqlMapper.update(builder);
	}
	/**
	 * 테이블 데이터 삭제
	 * @param builder
	 */
	public <T extends BaseVO> void deleteCommit(SQLBuilder<T> builder) {
		this.sqlMapper.delete(builder);
	}
	
	/**
	 * 테이블 데이터 삽입
	 * @param builder
	 */
	public <T extends BaseVO> int insertCommit(SQLBuilder<T> builder) {
		this.sqlMapper.insert(builder);
		int nextId = builder.getNextPKId();
		return nextId;
	}
	
	protected <T extends BaseVO> List<T> convertListToTarget(SQLBuilder<T> builder, List<Map<String, Object>> fetchedData) {
		Class<T> classInfo = builder.getClassInfo();
		String className = classInfo.getName();
		List<T> list = new ArrayList();
		try {
			T instance = (T) factory.getInstance(className);
			
			for(Map<String, Object> data : fetchedData) {
				Field[] fields  = classInfo.getDeclaredFields();
				Set<String> tableColumns = data.keySet();
				
				for(Field field : fields) {
					Column column = field.getDeclaredAnnotation(Column.class);
					if(column != null) {
						String declaredColumn = column.value();
						for(String tableColumn : tableColumns) {
							if(tableColumn.equalsIgnoreCase(declaredColumn)) {
								field.setAccessible(true);
								Object value = data.get(declaredColumn);
								if(value != null) {
									if(value instanceof BigDecimal) {
										BigDecimal bg = ((BigDecimal) value) ;
										if(this.isIntegerValue(bg)) field.set(instance, bg.intValue());
										else field.set(instance, bg.doubleValue());
									}
//									else if (value instanceof TIMESTAMP)
//										field.set(instance, ((TIMESTAMP)value).dateValue());
//									else if(value instanceof CLOB)
//										field.set(instance, ((CLOB)value).stringValue());
									else
										field.set(instance, (value));
									
								}
								
								field.setAccessible(false);
								break;
							}
						}
					}
				}
				list.add(instance);
				instance = (T) instance.newInstance();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}