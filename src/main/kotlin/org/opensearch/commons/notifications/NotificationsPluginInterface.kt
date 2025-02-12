/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 *
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

/*
 * Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.opensearch.commons.notifications

import org.opensearch.action.ActionListener
import org.opensearch.action.ActionResponse
import org.opensearch.client.node.NodeClient
import org.opensearch.common.io.stream.Writeable
import org.opensearch.commons.ConfigConstants.OPENSEARCH_SECURITY_USER_INFO_THREAD_CONTEXT
import org.opensearch.commons.notifications.NotificationConstants.FEATURE_INDEX_MANAGEMENT
import org.opensearch.commons.notifications.action.BaseResponse
import org.opensearch.commons.notifications.action.CreateNotificationConfigRequest
import org.opensearch.commons.notifications.action.CreateNotificationConfigResponse
import org.opensearch.commons.notifications.action.DeleteNotificationConfigRequest
import org.opensearch.commons.notifications.action.DeleteNotificationConfigResponse
import org.opensearch.commons.notifications.action.GetFeatureChannelListRequest
import org.opensearch.commons.notifications.action.GetFeatureChannelListResponse
import org.opensearch.commons.notifications.action.GetNotificationConfigRequest
import org.opensearch.commons.notifications.action.GetNotificationConfigResponse
import org.opensearch.commons.notifications.action.GetNotificationEventRequest
import org.opensearch.commons.notifications.action.GetNotificationEventResponse
import org.opensearch.commons.notifications.action.GetPluginFeaturesRequest
import org.opensearch.commons.notifications.action.GetPluginFeaturesResponse
import org.opensearch.commons.notifications.action.LegacyPublishNotificationRequest
import org.opensearch.commons.notifications.action.LegacyPublishNotificationResponse
import org.opensearch.commons.notifications.action.NotificationsActions.CREATE_NOTIFICATION_CONFIG_ACTION_TYPE
import org.opensearch.commons.notifications.action.NotificationsActions.DELETE_NOTIFICATION_CONFIG_ACTION_TYPE
import org.opensearch.commons.notifications.action.NotificationsActions.GET_FEATURE_CHANNEL_LIST_ACTION_TYPE
import org.opensearch.commons.notifications.action.NotificationsActions.GET_NOTIFICATION_CONFIG_ACTION_TYPE
import org.opensearch.commons.notifications.action.NotificationsActions.GET_NOTIFICATION_EVENT_ACTION_TYPE
import org.opensearch.commons.notifications.action.NotificationsActions.GET_PLUGIN_FEATURES_ACTION_TYPE
import org.opensearch.commons.notifications.action.NotificationsActions.LEGACY_PUBLISH_NOTIFICATION_ACTION_TYPE
import org.opensearch.commons.notifications.action.NotificationsActions.SEND_NOTIFICATION_ACTION_TYPE
import org.opensearch.commons.notifications.action.NotificationsActions.UPDATE_NOTIFICATION_CONFIG_ACTION_TYPE
import org.opensearch.commons.notifications.action.SendNotificationRequest
import org.opensearch.commons.notifications.action.SendNotificationResponse
import org.opensearch.commons.notifications.action.UpdateNotificationConfigRequest
import org.opensearch.commons.notifications.action.UpdateNotificationConfigResponse
import org.opensearch.commons.notifications.model.ChannelMessage
import org.opensearch.commons.notifications.model.EventSource
import org.opensearch.commons.utils.SecureClientWrapper
import org.opensearch.commons.utils.recreateObject

/**
 * All the transport action plugin interfaces for the Notification plugin
 */
object NotificationsPluginInterface {

    /**
     * Create notification configuration.
     * @param client Node client for making transport action
     * @param request The request object
     * @param listener The listener for getting response
     */
    fun createNotificationConfig(
        client: NodeClient,
        request: CreateNotificationConfigRequest,
        listener: ActionListener<CreateNotificationConfigResponse>
    ) {
        client.execute(
            CREATE_NOTIFICATION_CONFIG_ACTION_TYPE,
            request,
            wrapActionListener(listener) { response -> recreateObject(response) { CreateNotificationConfigResponse(it) } }
        )
    }

    /**
     * Update notification configuration.
     * @param client Node client for making transport action
     * @param request The request object
     * @param listener The listener for getting response
     */
    fun updateNotificationConfig(
        client: NodeClient,
        request: UpdateNotificationConfigRequest,
        listener: ActionListener<UpdateNotificationConfigResponse>
    ) {
        client.execute(
            UPDATE_NOTIFICATION_CONFIG_ACTION_TYPE,
            request,
            wrapActionListener(listener) { response -> recreateObject(response) { UpdateNotificationConfigResponse(it) } }
        )
    }

    /**
     * Delete notification configuration.
     * @param client Node client for making transport action
     * @param request The request object
     * @param listener The listener for getting response
     */
    fun deleteNotificationConfig(
        client: NodeClient,
        request: DeleteNotificationConfigRequest,
        listener: ActionListener<DeleteNotificationConfigResponse>
    ) {
        client.execute(
            DELETE_NOTIFICATION_CONFIG_ACTION_TYPE,
            request,
            wrapActionListener(listener) { response -> recreateObject(response) { DeleteNotificationConfigResponse(it) } }
        )
    }

    /**
     * Get notification configuration.
     * @param client Node client for making transport action
     * @param request The request object
     * @param listener The listener for getting response
     */
    fun getNotificationConfig(
        client: NodeClient,
        request: GetNotificationConfigRequest,
        listener: ActionListener<GetNotificationConfigResponse>
    ) {
        client.execute(
            GET_NOTIFICATION_CONFIG_ACTION_TYPE,
            request,
            wrapActionListener(listener) { response -> recreateObject(response) { GetNotificationConfigResponse(it) } }
        )
    }

    /**
     * Get notification events.
     * @param client Node client for making transport action
     * @param request The request object
     * @param listener The listener for getting response
     */
    fun getNotificationEvent(
        client: NodeClient,
        request: GetNotificationEventRequest,
        listener: ActionListener<GetNotificationEventResponse>
    ) {
        client.execute(
            GET_NOTIFICATION_EVENT_ACTION_TYPE,
            request,
            wrapActionListener(listener) { response -> recreateObject(response) { GetNotificationEventResponse(it) } }
        )
    }

    /**
     * Get notification plugin features.
     * @param client Node client for making transport action
     * @param request The request object
     * @param listener The listener for getting response
     */
    fun getPluginFeatures(
        client: NodeClient,
        request: GetPluginFeaturesRequest,
        listener: ActionListener<GetPluginFeaturesResponse>
    ) {
        client.execute(
            GET_PLUGIN_FEATURES_ACTION_TYPE,
            request,
            wrapActionListener(listener) { response -> recreateObject(response) { GetPluginFeaturesResponse(it) } }
        )
    }

    /**
     * Get notification channel configuration enabled for a feature.
     * @param client Node client for making transport action
     * @param request The request object
     * @param listener The listener for getting response
     */
    fun getFeatureChannelList(
        client: NodeClient,
        request: GetFeatureChannelListRequest,
        listener: ActionListener<GetFeatureChannelListResponse>
    ) {
        client.execute(
            GET_FEATURE_CHANNEL_LIST_ACTION_TYPE,
            request,
            wrapActionListener(listener) { response -> recreateObject(response) { GetFeatureChannelListResponse(it) } }
        )
    }

    /**
     * Send notification API enabled for a feature. No REST API. Internal API only for Inter plugin communication.
     * @param client Node client for making transport action
     * @param eventSource The notification event information
     * @param channelMessage The notification message
     * @param channelIds The list of channel ids to send message to.
     * @param listener The listener for getting response
     */
    fun sendNotification(
        client: NodeClient,
        eventSource: EventSource,
        channelMessage: ChannelMessage,
        channelIds: List<String>,
        listener: ActionListener<SendNotificationResponse>
    ) {
        val threadContext: String? =
            client.threadPool().threadContext.getTransient<String>(OPENSEARCH_SECURITY_USER_INFO_THREAD_CONTEXT)
        val wrapper = SecureClientWrapper(client) // Executing request in privileged mode
        wrapper.execute(
            SEND_NOTIFICATION_ACTION_TYPE,
            SendNotificationRequest(eventSource, channelMessage, channelIds, threadContext),
            wrapActionListener(listener) { response -> recreateObject(response) { SendNotificationResponse(it) } }
        )
    }

    /**
     * Publishes a notification API using the legacy notification implementation. No REST API.
     * Internal API only for the Index Management plugin.
     * @param client Node client for making transport action
     * @param request The legacy publish notification request
     * @param listener The listener for getting response
     */
    fun publishLegacyNotification(
        client: NodeClient,
        request: LegacyPublishNotificationRequest,
        listener: ActionListener<LegacyPublishNotificationResponse>
    ) {
        if (request.feature != FEATURE_INDEX_MANAGEMENT) {
            // Do not change this; do not pass in FEATURE_INDEX_MANAGEMENT if you are not the Index Management plugin.
            throw IllegalArgumentException("The publish notification method only supports the Index Management feature.")
        }

        client.execute(
            LEGACY_PUBLISH_NOTIFICATION_ACTION_TYPE,
            request,
            wrapActionListener(listener) { response -> recreateObject(response) { LegacyPublishNotificationResponse(it) } }
        )
    }

    /**
     * Wrap action listener on concrete response class by a new created one on ActionResponse.
     * This is required because the response may be loaded by different classloader across plugins.
     * The onResponse(ActionResponse) avoids type cast exception and give a chance to recreate
     * the response object.
     */
    @Suppress("UNCHECKED_CAST")
    private fun <Response : BaseResponse> wrapActionListener(
        listener: ActionListener<Response>,
        recreate: (Writeable) -> Response
    ): ActionListener<Response> {
        return object : ActionListener<ActionResponse> {
            override fun onResponse(response: ActionResponse) {
                val recreated = response as? Response ?: recreate(response)
                listener.onResponse(recreated)
            }

            override fun onFailure(exception: java.lang.Exception) {
                listener.onFailure(exception)
            }
        } as ActionListener<Response>
    }
}
