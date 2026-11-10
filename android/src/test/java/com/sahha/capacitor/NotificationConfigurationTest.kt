package com.sahha.capacitor

import ai.sahha.api.notifications.SahhaNotificationConfiguration
import org.junit.Assert.assertEquals
import org.junit.Test

class NotificationConfigurationTest {

    private val default = SahhaNotificationConfiguration.DEFAULT

    @Test
    fun `no values set uses the SDK defaults`() {
        val config = notificationConfiguration(
            iconResourceId = null,
            title = null,
            shortDescription = null,
        )

        assertEquals(default, config)
    }

    @Test
    fun `only a title set keeps it and defaults the icon and description`() {
        val config = notificationConfiguration(
            iconResourceId = null,
            title = "My App",
            shortDescription = null,
        )

        assertEquals(
            SahhaNotificationConfiguration(default.iconResourceId, "My App", default.shortDescription),
            config,
        )
    }

    @Test
    fun `a missing title defaults while the icon and description are kept`() {
        val config = notificationConfiguration(
            iconResourceId = 42,
            title = null,
            shortDescription = "Syncing your health data",
        )

        assertEquals(
            SahhaNotificationConfiguration(42, default.title, "Syncing your health data"),
            config,
        )
    }

    @Test
    fun `all values set are kept`() {
        val config = notificationConfiguration(
            iconResourceId = 42,
            title = "My App",
            shortDescription = "Syncing your health data",
        )

        assertEquals(SahhaNotificationConfiguration(42, "My App", "Syncing your health data"), config)
    }
}
