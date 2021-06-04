package com.example.springsecurity.configuration;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan("com.example.springsecurity.repository")
public class MyBatisConfiguration {

  private DataSource dataSource;
  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  @Bean
  public DataSourceTransactionManager dataSourceTransactionManager() {
    return new DataSourceTransactionManager(dataSource);
  }
  @Bean
  public SqlSessionFactoryBean sqlSessionFactoryBean() {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    return sqlSessionFactoryBean;
  }


}
