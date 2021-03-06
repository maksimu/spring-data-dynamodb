package org.socialsignin.spring.data.dynamodb.mapping.event;

/*
 * Copyright 2014 by the original author(s).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.GenericTypeResolver;

/**
 * Base class to implement domain class specific {@link ApplicationListener}s.
 *
 * @author Michael Lavelle
 */
public abstract class AbstractDynamoDBEventListener<E> implements
		ApplicationListener<DynamoDBMappingEvent<?>> {

	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractDynamoDBEventListener.class);
	private final Class<?> domainClass;

	/**
	 * Creates a new {@link AbstractDynamoDBEventListener}.
	 */
	public AbstractDynamoDBEventListener() {
		Class<?> typeArgument = GenericTypeResolver.resolveTypeArgument(
				this.getClass(), AbstractDynamoDBEventListener.class);
		this.domainClass = typeArgument == null ? Object.class : typeArgument;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org
	 * .springframework.context.ApplicationEvent)
	 */
	@Override
    public void onApplicationEvent(DynamoDBMappingEvent<?> event) {

		@SuppressWarnings("unchecked")
		E source = (E) event.getSource();

		// Check for matching domain type and invoke callbacks
		if (source != null && !domainClass.isAssignableFrom(source.getClass())) {
			return;
		}

		if (event instanceof BeforeSaveEvent) {
			onBeforeSave(source);
		} else if (event instanceof AfterSaveEvent) {
			onAfterSave(source);
		} else if (event instanceof BeforeDeleteEvent) {
			onBeforeDelete(source);
		} else if (event instanceof AfterDeleteEvent) {
			onAfterDelete(source);
		} else if (event instanceof AfterLoadEvent) {
			onAfterLoad(source);
		} else if (event instanceof AfterScanEvent) {
			onAfterScan(source);
		} else if (event instanceof AfterQueryEvent) {
			onAfterQuery(source);
		}
	}

	public void onBeforeSave(E source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onBeforeSave({}, {})", source);
		}
	}

	public void onAfterSave(E source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterSave({}, {})", source);
		}
	}

	public void onAfterLoad(E source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterLoad({})", source);
		}
	}

	public void onAfterDelete(E source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterDelete({})", source);
		}
	}

	public void onBeforeDelete(E source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onBeforeDelete({})", source);
		}
	}

	public void onAfterScan(E source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterScan({})", source);
		}
	}

	public void onAfterQuery(E source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterQuery({})", source);
		}
	}

}
