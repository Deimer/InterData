package com.testdeymervilla.network.constants

object NetworkConstants {

    object Defaults {
        const val DEFAULT_TIMEOUT = 10L
    }

    object Headers {
        const val USER = "Usuario"
        const val IDENTIFICATION = "Identificacion"
        const val ACCEPT = "Accept"
        const val USER_ID = "IdUsuario"
        const val SERVICE_CENTER_ID = "IdCentroServicio"
        const val SERVICE_CENTER_NAME = "NombreCentroServicio"
        const val ORIGIN_APP_ID = "IdAplicativoOrigen"
        const val CONTENT_TYPE = "Content-Type"
        const val ACCEPT_JSON = "text/json"
        const val CONTENT_TYPE_JSON = "application/json"
    }

    object HeaderValues {
        const val USER = "pam.meredy21"
        const val IDENTIFICATION = "987204545"
        const val USER_ID = "pam.meredy21"
        const val SERVICE_CENTER_ID = "1295"
        const val SERVICE_CENTER_NAME = "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45"
        const val ORIGIN_APPLICATION_ID = "9"
    }

    object Paths {
        object Framework {
            const val APP_VERSION = "apicontrollerpruebas/api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl"
            const val LOCALITIES = "apicontrollerpruebas/api/ParametrosFramework/ObtenerLocalidadesRecogidas"
        }
        object Security {
            const val LOGIN = "FtEntregaElectronica/MultiCanales/ApiSeguridadPruebas/api/Seguridad/AuthenticaUsuarioApp"
        }
        object Synchronization {
            const val SCHEMA = "apicontrollerpruebas/api/SincronizadorDatos/ObtenerEsquema/true"
        }
    }
}