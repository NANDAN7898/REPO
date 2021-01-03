package com.schoolmgmt.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
public class WebAppConfig
{
	@Bean(value = { "tilesConfig" })
	public TilesConfigurer tilesConfigurer()
	{
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("/WEB-INF/TilesMapping.xml");
		return tilesConfigurer;
	}

	/**
	 * @return
	 */
	@Bean(value = { "handlerMapping" })
	public HandlerMapping getHandlerMapping()
	{
		return new RequestMappingHandlerMapping();
	}

	/**
	 * @param negotiatingManager
	 * @return
	 */
	@Bean(value = { "viewResolver" })
	public ViewResolver getViewResolver(ContentNegotiationManager negotiatingManager)
	{
		List<ViewResolver> viewResolverList = new ArrayList<>();
		ContentNegotiatingViewResolver negotiatingResolver = new ContentNegotiatingViewResolver();

		UrlBasedViewResolver urlResolver = new UrlBasedViewResolver();
		urlResolver.setViewClass(TilesView.class);
		viewResolverList.add(urlResolver);

		InternalResourceViewResolver internalResResolver = new InternalResourceViewResolver();
		internalResResolver.setPrefix("/WEB-INF/pages/");
		internalResResolver.setSuffix(".jsp");
		viewResolverList.add(internalResResolver);

		negotiatingResolver.setViewResolvers(viewResolverList);
		negotiatingResolver.setContentNegotiationManager(negotiatingManager);

		return negotiatingResolver;
	}

}
