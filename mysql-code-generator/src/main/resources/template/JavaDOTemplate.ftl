package ${object.packageStr};

public class ${object.name} {
	
	<#list object.props as item>
	private ${item.propType} ${item.propName};
		
	</#list>
	
	
	<#list object.props as item>
	public void set${item.pascaPropName} (${item.propType} ${item.propName}) {
		this.${item.propName} = ${item.propName};
	}
	
	
	public ${item.propType} get${item.pascaPropName} () {
		return this.${item.propName};
	}
		
	</#list>
	
}