package com.mrh.spring.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrh.spring.generator.model.Column;
import com.mrh.spring.generator.model.JavaDOModel;
import com.mrh.spring.generator.model.JavaDOPropModel;
import com.mrh.spring.generator.model.Table;
import com.mrh.spring.generator.util.DatabaseUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class JavaDOGenerator {
	
	private static final String TEMPLATE_PATH = "src/main/resource/template";
	private static final String CLASS_PATH = "src/main/java/com/mrh/spring/generator/model";
	private static final String PACKAGE = "com.mrh.spring.generator.model";

	/**
	 * 生成实体类
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @param tableName
	 */
	public static void generatorJavaDO(String url, String username, String password, String tableName) {
		Connection conn = DatabaseUtil.getConnection(url, username, password);
		Table table = DatabaseUtil.getTableColumns(conn, tableName);
		if (table == null) {
			System.out.println("table " + tableName + " has not exist");
			return;
		}
		JavaDOModel javaDOModel = convertTableToJavaDOModel(table);
		if (javaDOModel == null) {
			System.out.println("table " + tableName + " has not exist");
			return;
		}
		generator(javaDOModel, javaDOModel.getName()+".java", "javaDOTemplate.ftl");
		
		DatabaseUtil.closeConnection(conn);
	}
	
	private static void generator(Object o, String fileName, String templateName) {
		Configuration configuration = new Configuration();
        Writer out = null;
        String path = Thread.currentThread().getContextClassLoader().getResource("template").getPath();
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(path));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("object", o);
            // step4 加载模版文件
            Template template = configuration.getTemplate(templateName);
            // step5 生成数据
            File docFile = new File(CLASS_PATH + "/" + fileName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^"+fileName+" 文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
	}

	private static JavaDOModel convertTableToJavaDOModel(Table table) {
		if (table == null) {
			return null;
		}
		JavaDOModel model = new JavaDOModel();
		model.setName(convertSnakeToPasca(table.getTableName()));
		model.setPackageStr(PACKAGE);
		List<JavaDOPropModel> props = new ArrayList<>();
		for(Column c : table.getColumns()) {
			JavaDOPropModel prop = convertColumnToJavaDOPropModel(c);
			if(prop == null) {
				continue;
			}
			props.add(prop);
		}
		model.setProps(props);
		return model;
	}
	
	private static JavaDOPropModel convertColumnToJavaDOPropModel(Column column) {
		if(column == null) {
			return null;
		}
		JavaDOPropModel model = new JavaDOPropModel();
		model.setPropName(convertSnakeToCamel(column.getColumnName()));
		model.setPropRemark(column.getReference());
		model.setPropType(convertDatabaseTypeToJavaType(column.getColumnType()));
		model.setPascaPropName(convertCamelToPasca(model.getPropName()));
		return model;
	}
	
	private static String convertDatabaseTypeToJavaType(String dbType) {
		List<String> integerTypes = Arrays.asList("TINYINT", "SMALLINT", "MEDIUMINT", "INT", "INTEGER");
		List<String> longTypes = Arrays.asList("TIMESTAMP", "BIGINT");
		List<String> floatTypes = Arrays.asList("FLOAT");
		List<String> doubleTypes = Arrays.asList("DECIMAL");
		List<String> stringTypes = Arrays.asList("VARCHAR", "TINYBLOB", "CHAR", "TINYTEXT", "BLOB", "TEXT", "MEDIUMBLOB", "MEDIUMTEXT", "LONGBLOB", "LONGTEXT");
		List<String> dateTypes = Arrays.asList("DATETIME", "DATE");
		
		if(integerTypes.contains(dbType)) {
			return "Integer";
		}
		
		if(longTypes.contains(dbType)) {
			return "Long";
		}
		
		if(floatTypes.contains(dbType)) {
			return "Float";
		}
		
		if(doubleTypes.contains(dbType)) {
			return "Double";
		}
		
		if(stringTypes.contains(dbType)) {
			return "String";
		}
		
		if(dateTypes.contains(dbType)) {
			return "Date";
		}
		
		return "";
	}

	/**
	 * 由下划线(蛇型）命名法，转化成驼峰命名法
	 * 
	 * @param name
	 */
	private static String convertSnakeToCamel(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}

		char[] nameChars = name.toCharArray();
		boolean previousLine = false;
		for (int i=0; i<nameChars.length; i++) {
			if (previousLine) {
				nameChars[i] -= 32;
				previousLine = false;
			}
			if (nameChars[i] == "_".charAt(0)) {
				previousLine = true;
			}
		}
		return String.valueOf(nameChars).replaceAll("_", "");
	}

	/**
	 * 由下划线(蛇型）命名法，转化成帕斯卡命名法
	 * 
	 * @param name
	 * @return
	 */
	private static String convertSnakeToPasca(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		name = convertSnakeToCamel(name);
		char[] nameChars = name.toCharArray();
		nameChars[0] -= 32;
		return String.valueOf(nameChars);
	}
	
	private static String convertCamelToPasca(String name) {
		char[] nameChars = name.toCharArray();
		nameChars[0] -= 32;
		return String.valueOf(nameChars);
	}

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/LocalTest?useUnicode=true&characterEncoding=UTF-8";
		String username = "root";
		String password = "12345678";
		
		// 初始化
		DatabaseUtil.init();
		generatorJavaDO(url, username, password, "m_user");
	}
}
