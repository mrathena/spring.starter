package mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Generator {

	/**
	 * 当你从线上clone最新代码下来的时候，难免有些配置文件会和本地环境需求不一样。
	 * 所以当你更改了这些配置文件的时候想要它不出现在更改列表里，这个时候git的这条命令就起作用了。
	 * git update-index --assume-unchanged  文件名 #忽略跟踪
	 * git update-index --no-assume-unchanged  文件名 #恢复跟踪
	 */
	public static void main(String[] args) {

		List<String> warnings = new LinkedList<>();
		try {
//			System.out.println(Generator.class.getResource("/"));
//			System.out.println(Generator.class.getResource(""));
//			System.out.println(Generator.class.getResource("/mybatis.generator/mybatis.generator.xml"));
//			System.out.println(Generator.class.getResource("/mybatis.generator/mybatis.generator.properties"));
			ConfigurationParser parser = new ConfigurationParser(warnings);
			String absolutePath = Generator.class.getResource("/mybatis.generator/mybatis.generator.xml").getPath();
			Configuration config = parser.parseConfiguration(new File(absolutePath));
			DefaultShellCallback callback = new DefaultShellCallback(true);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (warnings.size() == 0) {
			System.out.println("OK");
		}
		for (String warning : warnings) {
			System.out.println(warning);
		}
	}

}