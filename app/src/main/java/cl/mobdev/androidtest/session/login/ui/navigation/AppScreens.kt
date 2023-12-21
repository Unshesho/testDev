package cl.mobdev.androidtest.session.login.ui.navigation

sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens(SPLASHSCREEN)
    object LoginScreen : AppScreens(LOGINSCREEN)
    object OnBoardingCreateEvent : AppScreens(ONBOARDING_CREATE_EVENT)
    object OnBoardingJoinEvent : AppScreens(ONBOARDING_JOIN_EVENT)
    object OnBoardingPaymentsReceipts : AppScreens(ONBOARDING_PAYMENTS_RECEIPTS)
    object HomeMockScreen : AppScreens(HOMEMOCKSCREEN)
    object CalendarDialogScreen : AppScreens(CALENDARSCREEN)
    object MistakeDialogScreen : AppScreens(CREATE_EVENTSCREEN)
    object NotificationDialogScreen : AppScreens(NOTIFICATIONSCREEN)
    object DialogExitScreen : AppScreens(DIALOGEXITSCREEN)
    object EventTypeScreen : AppScreens(EVENTTYPESCREEN)
    object HowWillScreen : AppScreens(HOWWILLSCREEN)
    object NewEventScreen : AppScreens(NEWEVENTSCREEN)
    object EventLocationScreen : AppScreens(EVENTLOCATIONSCREEN)
    object WhenEverOneScreen : AppScreens(WHENEVERONESCREEN)
    object WhenEverTwoScreen : AppScreens(WHENEVERTWOSCREEN)
    object WhenEverThreeScreen : AppScreens(WHENEVERTHRESCREEN)
    object WhenEverFourScreen : AppScreens(WHENEVERFOURSCREEN)
    object WhenEverFiveScreen : AppScreens(WHENEVERFIVESCREEN)
    object HomeScreen : AppScreens(HOMESCREEN)
    object ListEventScreen: AppScreens(LISTEVENTSCREEN)
    object EventDescriptionScreen : AppScreens(DESCRIPTIONSCREEN)
    object EventPaymentScreen : AppScreens(EVENTPAYMENT)
    object EventNamePaymentScreen : AppScreens(EVENTNAMESPAYMENT)
    object SuccessLogin : AppScreens(SUCCESS_LOGIN)
    object RegisterScreen : AppScreens(REGISTER_SCREEN)
    object LoginPadok : AppScreens(route = LOGIN_PADOK)
    object EditScreen : AppScreens(route = EDIT_EVENT)
    object DetailEvent : AppScreens(route = DETAIL_EVENT)
    object UpdateEventScreen : AppScreens(route = UPDATE_EVENT)
    object DetailPaymentScreen : AppScreens(route = DETAIL_PAYMENT)
    object PaymentScreen : AppScreens(route = PAYMENT)
    object PublicEventScreen : AppScreens(route = PUBLIC_EVENT)

    object ErrorScreen : AppScreens(route = PUBLIC_EVENT)

    companion object {
        const val SPLASHSCREEN = "Splash"
        const val LOGINSCREEN = "Login"
        const val ONBOARDING_CREATE_EVENT = "OnBoardingCreateEvent"
        const val ONBOARDING_JOIN_EVENT = "OnBoardingJoinEvent"
        const val ONBOARDING_PAYMENTS_RECEIPTS = "OnBoardingPaymentsReceipts"
        const val HOMEMOCKSCREEN = "homeMock"
        const val CALENDARSCREEN = "calendar"
        const val CREATE_EVENTSCREEN = "create_event"
        const val NOTIFICATIONSCREEN = "notification"
        const val DIALOGEXITSCREEN = "dialog_exit"
        const val EVENTTYPESCREEN = "event_type"
        const val HOWWILLSCREEN = "how_will"
        const val NEWEVENTSCREEN = "new_event"
        const val EVENTLOCATIONSCREEN = "event_location"
        const val WHENEVERONESCREEN = "when_one"
        const val WHENEVERTWOSCREEN = "when_two"
        const val WHENEVERTHRESCREEN = "when_three"
        const val WHENEVERFOURSCREEN = "when_four"
        const val WHENEVERFIVESCREEN = "when_five"
        const val HOMESCREEN = "home_screen"
        const val LISTEVENTSCREEN = "list_event_screen"
        const val DESCRIPTIONSCREEN = "event_description"
        const val EVENTPAYMENT = "event_payment"
        const val EVENTNAMESPAYMENT = "event_name"
        const val SUCCESS_LOGIN = "success_screen"
        const val REGISTER_SCREEN = "register_screen"
        const val DETAIL_EVENT = "detail_event"
        const val EDIT_EVENT = "edit_event"
        const val UPDATE_EVENT = "update_event"
        const val LOGIN_PADOK = "login_padok"
        const val PUBLIC_EVENT = "public_event"
        const val DETAIL_PAYMENT = "detail_payment"
        const val PAYMENT = "payment"
        const val ERROR_SCREEN = "error_screen"
    }
}