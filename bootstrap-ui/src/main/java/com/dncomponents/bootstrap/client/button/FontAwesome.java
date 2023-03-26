/*
 * Copyright 2023 dncomponents
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

package com.dncomponents.bootstrap.client.button;

import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;
import elemental2.dom.HTMLElement;

public enum FontAwesome implements HasStyle {
    ADDRESS_BOOK,
    ADDRESS_BOOK_O,
    ADDRESS_CARD,
    ADDRESS_CARD_O,
    ADJUST,
    AMERICAN_SIGN_LANGUAGE_INTERPRETING,
    ANCHOR,
    ARCHIVE,
    AREA_CHART,
    ARROWS,
    ARROWS_H,
    ARROWS_V,
    ASL_INTERPRETING("american-sign-language-interpreting"),
    ASSISTIVE_LISTENING_SYSTEMS,
    ASTERISK,
    AT,
    AUDIO_DESCRIPTION,
    AUTOMOBILE("car"),
    BALANCE_SCALE,
    BAN,
    BANK("university"),
    BAR_CHART,
    BAR_CHART_O("bar-chart"),
    BARCODE,
    BARS,
    BATH,
    BATHTUB("bath"),
    BATTERY("battery-full"),
    BATTERY_0("battery-empty"),
    BATTERY_1("battery-quarter"),
    BATTERY_2("battery-half"),
    BATTERY_3("battery-three-quarters"),
    BATTERY_4("battery-full "),
    BATTERY_EMPTY,
    BATTERY_FULL,
    BATTERY_HALF,
    BATTERY_QUARTER,
    BATTERY_THREE_QUARTERS,
    BED,
    BEER,
    BELL,
    BELL_O,
    BELL_SLASH,
    BELL_SLASH_O,
    BICYCLE,
    BINOCULARS,
    BIRTHDAY_CAKE,
    BLIND,
    BLUETOOTH,
    BLUETOOTH_B,
    BOLT,
    BOMB,
    BOOK,
    BOOKMARK,
    BOOKMARK_O,
    BRAILLE,
    BRIEFCASE,
    BUG,
    BUILDING,
    BUILDING_O,
    BULLHORN,
    BULLSEYE,
    BUS,
    CAB("taxi"),
    CALCULATOR,
    CALENDAR,
    CALENDAR_CHECK_O,
    CALENDAR_MINUS_O,
    CALENDAR_O,
    CALENDAR_PLUS_O,
    CALENDAR_TIMES_O,
    CAMERA,
    CAMERA_RETRO,
    CAR,
    CARET_SQUARE_O_DOWN,
    CARET_SQUARE_O_LEFT,
    CARET_SQUARE_O_RIGHT,
    CARET_SQUARE_O_UP,
    CART_ARROW_DOWN,
    CART_PLUS,
    CC,
    CERTIFICATE,
    CHECK,
    CHECK_CIRCLE,
    CHECK_CIRCLE_O,
    CHECK_SQUARE,
    CHECK_SQUARE_O,
    CHILD,
    CIRCLE,
    CIRCLE_O,
    CIRCLE_O_NOTCH,
    CIRCLE_THIN,
    CLOCK_O,
    CLONE,
    CLOSE("times"),
    CLOUD,
    CLOUD_DOWNLOAD,
    CLOUD_UPLOAD,
    CODE,
    CODE_FORK,
    COFFEE,
    COG,
    COGS,
    COMMENT,
    COMMENT_O,
    COMMENTING,
    COMMENTING_O,
    COMMENTS,
    COMMENTS_O,
    COMPASS,
    COPYRIGHT,
    CREATIVE_COMMONS,
    CREDIT_CARD,
    CREDIT_CARD_ALT,
    CROP,
    CROSSHAIRS,
    CUBE,
    CUBES,
    CUTLERY,
    DASHBOARD("tachometer"),
    DATABASE,
    DEAF,
    DEAFNESS("deaf"),
    DESKTOP,
    DIAMOND,
    DOT_CIRCLE_O,
    DOWNLOAD,
    DRIVERS_LICENSE("id-card"),
    DRIVERS_LICENSE_O("id-card-o"),
    EDIT("pencil-square-o"),
    ELLIPSIS_H,
    ELLIPSIS_V,
    ENVELOPE,
    ENVELOPE_O,
    ENVELOPE_OPEN,
    ENVELOPE_OPEN_O,
    ENVELOPE_SQUARE,
    ERASER,
    EXCHANGE,
    EXCLAMATION,
    EXCLAMATION_CIRCLE,
    EXCLAMATION_TRIANGLE,
    EXTERNAL_LINK,
    EXTERNAL_LINK_SQUARE,
    EYE,
    EYE_SLASH,
    EYEDROPPER,
    FAX,
    FEED("rss"),
    FEMALE,
    FIGHTER_JET,
    FILE_ARCHIVE_O,
    FILE_AUDIO_O,
    FILE_CODE_O,
    FILE_EXCEL_O,
    FILE_IMAGE_O,
    FILE_PDF_O,
    FILE_PHOTO_O("file-image-o"),
    FILE_PICTURE_O("file-image-o"),
    FILE_POWERPOINT_O,
    FILE_SOUND_O("file-audio-o"),
    FILE_VIDEO_O,
    FILE_WORD_O,
    FILE_ZIP_O("file-archive-o"),
    FILM,
    FILTER,
    FIRE,
    FIRE_EXTINGUISHER,
    FLAG,
    FLAG_CHECKERED,
    FLAG_O,
    FLASH("bolt"),
    FLASK,
    FOLDER,
    FOLDER_O,
    FOLDER_OPEN,
    FOLDER_OPEN_O,
    FROWN_O,
    FUTBOL_O,
    GAMEPAD,
    GAVEL,
    GEAR("cog"),
    GEARS("cogs"),
    GIFT,
    GLASS,
    GLOBE,
    GRADUATION_CAP,
    GROUP("users"),
    HAND_GRAB_O("hand-rock-o"),
    HAND_LIZARD_O,
    HAND_PAPER_O,
    HAND_PEACE_O,
    HAND_POINTER_O,
    HAND_ROCK_O,
    HAND_SCISSORS_O,
    HAND_SPOCK_O,
    HAND_STOP_O("hand-paper-o "),
    HANDSHAKE_O,
    HARD_OF_HEARING("deaf"),
    HASHTAG,
    HDD_O,
    HEADPHONES,
    HEART,
    HEART_O,
    HEARTBEAT,
    HISTORY,
    HOME,
    HOTEL("bed"),
    HOURGLASS,
    HOURGLASS_1("hourglass-start"),
    HOURGLASS_2("hourglass-half"),
    HOURGLASS_3("hourglass-end"),
    HOURGLASS_END,
    HOURGLASS_HALF,
    HOURGLASS_O,
    HOURGLASS_START,
    I_CURSOR,
    ID_BADGE,
    ID_CARD,
    ID_CARD_O,
    IMAGE("picture-o"),
    INBOX,
    INDUSTRY,
    INFO,
    INFO_CIRCLE,
    INSTITUTION("university"),
    KEY,
    KEYBOARD_O,
    LANGUAGE,
    LAPTOP,
    LEAF,
    LEGAL("gavel"),
    LEMON_O,
    LEVEL_DOWN,
    LEVEL_UP,
    LIFE_BOUY("life-ring"),
    LIFE_RING,
    LIFE_SAVER("life-ring"),
    LIGHTBULB_O,
    LINE_CHART,
    LOCATION_ARROW,
    LOCK,
    LOW_VISION,
    MAGIC,
    MAGNET,
    MAIL_FORWARD("share"),
    MAIL_REPLY("reply"),
    MAIL_REPLY_ALL("reply-all"),
    MALE,
    MAP,
    MAP_MARKER,
    MAP_O,
    MAP_PIN,
    MAP_SIGNS,
    MEH_O,
    MICROCHIP,
    MICROPHONE,
    MICROPHONE_SLASH,
    MINUS,
    MINUS_CIRCLE,
    MINUS_SQUARE,
    MINUS_SQUARE_O,
    MOBILE,
    MOBILE_PHONE("mobile"),
    MONEY,
    MOON_O,
    MORTAR_BOARD("graduation-cap"),
    MOTORCYCLE,
    MOUSE_POINTER,
    MUSIC,
    NAVICON("bars"),
    NEWSPAPER_O,
    OBJECT_GROUP,
    OBJECT_UNGROUP,
    PAINT_BRUSH,
    PAPER_PLANE,
    PAPER_PLANE_O,
    PAW,
    PENCIL,
    PENCIL_SQUARE,
    PENCIL_SQUARE_O,
    PERCENT,
    PHONE,
    PHONE_SQUARE,
    PHOTO("picture-o"),
    PICTURE_O,
    PIE_CHART,
    PLANE,
    PLUG,
    PLUS,
    PLUS_CIRCLE,
    PLUS_SQUARE,
    PLUS_SQUARE_O,
    PODCAST,
    POWER_OFF,
    PRINT,
    PUZZLE_PIECE,
    QRCODE,
    QUESTION,
    QUESTION_CIRCLE,
    QUESTION_CIRCLE_O,
    QUOTE_LEFT,
    QUOTE_RIGHT,
    RANDOM,
    RECYCLE,
    REFRESH,
    REGISTERED,
    REMOVE("times"),
    REORDER("bars"),
    REPLY,
    REPLY_ALL,
    RETWEET,
    ROAD,
    ROCKET,
    RSS,
    RSS_SQUARE,
    S15("bath"),
    SEARCH,
    SEARCH_MINUS,
    SEARCH_PLUS,
    SEND("paper-plane"),
    SEND_O("paper-plane-o"),
    SERVER,
    SHARE,
    SHARE_ALT,
    SHARE_ALT_SQUARE,
    SHARE_SQUARE,
    SHARE_SQUARE_O,
    SHIELD,
    SHIP,
    SHOPPING_BAG,
    SHOPPING_BASKET,
    SHOPPING_CART,
    SHOWER,
    SIGN_IN,
    SIGN_LANGUAGE,
    SIGN_OUT,
    SIGNAL,
    SIGNING("sign-language"),
    SITEMAP,
    SLIDERS,
    SMILE_O,
    SNOWFLAKE_O,
    SOCCER_BALL_O("futbol-o"),
    SORT,
    SORT_ALPHA_ASC,
    SORT_ALPHA_DESC,
    SORT_AMOUNT_ASC,
    SORT_AMOUNT_DESC,
    SORT_ASC,
    SORT_DESC,
    SORT_DOWN("sort-desc"),
    SORT_NUMERIC_ASC,
    SORT_NUMERIC_DESC,
    SORT_UP("sort-asc"),
    SPACE_SHUTTLE,
    SPINNER,
    SPOON,
    SQUARE,
    SQUARE_O,
    STAR,
    STAR_HALF,
    STAR_HALF_EMPTY("star-half-o"),
    STAR_HALF_FULL("star-half-o"),
    STAR_HALF_O,
    STAR_O,
    STICKY_NOTE,
    STICKY_NOTE_O,
    STREET_VIEW,
    SUITCASE,
    SUN_O,
    SUPPORT("life-ring"),
    TABLET,
    TACHOMETER,
    TAG,
    TAGS,
    TASKS,
    TAXI,
    TELEVISION,
    TERMINAL,
    THERMOMETER("thermometer-full"),
    THERMOMETER_0("thermometer-empty "),
    THERMOMETER_1("thermometer-quarter"),
    THERMOMETER_2("thermometer-half"),
    THERMOMETER_3("thermometer-three-quarters"),
    THERMOMETER_4("thermometer-full"),
    THERMOMETER_EMPTY,
    THERMOMETER_FULL,
    THERMOMETER_HALF,
    THERMOMETER_QUARTER,
    THERMOMETER_THREE_QUARTERS,
    THUMB_TACK,
    THUMBS_DOWN,
    THUMBS_O_DOWN,
    THUMBS_O_UP,
    THUMBS_UP,
    TICKET,
    TIMES,
    TIMES_CIRCLE,
    TIMES_CIRCLE_O,
    TIMES_RECTANGLE("window-close"),
    TIMES_RECTANGLE_O("window-close-o"),
    TINT,
    TOGGLE_DOWN("caret-square-o-down"),
    TOGGLE_LEFT("caret-square-o-left "),
    TOGGLE_OFF,
    TOGGLE_ON,
    TOGGLE_RIGHT("caret-square-o-right"),
    TOGGLE_UP("caret-square-o-up"),
    TRADEMARK,
    TRASH,
    TRASH_O,
    TREE,
    TROPHY,
    TRUCK,
    TTY,
    TV("television"),
    UMBRELLA,
    UNIVERSAL_ACCESS,
    UNIVERSITY,
    UNLOCK,
    UNLOCK_ALT,
    UNSORTED("sort"),
    UPLOAD,
    USER,
    USER_CIRCLE,
    USER_CIRCLE_O,
    USER_O,
    USER_PLUS,
    USER_SECRET,
    USER_TIMES,
    USERS,
    VCARD("address-card"),
    VCARD_O("address-card-o"),
    VIDEO_CAMERA,
    VOLUME_CONTROL_PHONE,
    VOLUME_DOWN,
    VOLUME_OFF,
    VOLUME_UP,
    WARNING("exclamation-triangle"),
    WHEELCHAIR,
    WHEELCHAIR_ALT,
    WIFI,
    WINDOW_CLOSE,
    WINDOW_CLOSE_O,
    WINDOW_MAXIMIZE,
    WINDOW_MINIMIZE,
    WINDOW_RESTORE,
    WRENCH,


    HAND_O_DOWN,
    HAND_O_LEFT,
    HAND_O_RIGHT,
    HAND_O_UP,

    AMBULANCE,
    SUBWAY,
    TRAIN,

    GENDERLESS,
    INTERSEX("transgender"),
    MARS,
    MARS_DOUBLE,
    MARS_STROKE,
    MARS_STROKE_H,
    MARS_STROKE_V,
    MERCURY,
    NEUTER,
    TRANSGENDER,
    TRANSGENDER_ALT,
    VENUS,
    VENUS_DOUBLE,
    VENUS_MARS,

    FILE,
    FILE_MOVIE_O("file-video-o"),
    FILE_O,
    FILE_TEXT,
    FILE_TEXT_O,


    CC_AMEX,
    CC_DINERS_CLUB,
    CC_DISCOVER,
    CC_JCB,
    CC_MASTERCARD,
    CC_PAYPAL,
    CC_STRIPE,
    CC_VISA,
    GOOGLE_WALLET,
    PAYPAL,
    BITCOIN("btc"),
    BTC,
    CNY("jpy"),
    DOLLAR("usd"),
    EUR,
    EURO("eur"),
    GBP,
    GG,
    GG_CIRCLE,
    ILS,
    INR,
    JPY,
    KRW,
    RMB("jpy"),
    ROUBLE("rub"),
    RUB,
    RUBLE("rub"),
    RUPEE("inr"),
    SHEKEL("ils"),
    SHEQEL("ils"),
    TRY,
    TURKISH_LIRA("try"),
    USD,
    WON("krw"),
    YEN("jpy"),

    ALIGN_CENTER,
    ALIGN_JUSTIFY,
    ALIGN_LEFT,
    ALIGN_RIGHT,
    BOLD,
    CHAIN("link"),
    CHAIN_BROKEN,
    CLIPBOARD,
    COLUMNS,
    COPY("files-o"),
    CUT("scissors"),
    DEDENT("outdent"),
    FILES_O,
    FLOPPY_O,
    FONT,
    HEADER,
    INDENT,
    ITALIC,
    LINK,
    LIST,
    LIST_ALT,
    LIST_OL,
    LIST_UL,
    OUTDENT,
    PAPERCLIP,
    PARAGRAPH,
    PASTE("clipboard"),
    REPEAT,
    ROTATE_LEFT("undo"),
    ROTATE_RIGHT("repeat"),
    SAVE("floppy-o"),
    SCISSORS,
    STRIKETHROUGH,
    SUBSCRIPT,
    SUPERSCRIPT,
    TABLE,
    TEXT_HEIGHT,
    TEXT_WIDTH,
    TH,
    TH_LARGE,
    TH_LIST,
    UNDERLINE,
    UNDO,
    UNLINK("chain-broken"),

    ANGLE_DOUBLE_DOWN,
    ANGLE_DOUBLE_LEFT,
    ANGLE_DOUBLE_RIGHT,
    ANGLE_DOUBLE_UP,
    ANGLE_DOWN,
    ANGLE_LEFT,
    ANGLE_RIGHT,
    ANGLE_UP,
    ARROW_CIRCLE_DOWN,
    ARROW_CIRCLE_LEFT,
    ARROW_CIRCLE_O_DOWN,
    ARROW_CIRCLE_O_LEFT,
    ARROW_CIRCLE_O_RIGHT,
    ARROW_CIRCLE_O_UP,
    ARROW_CIRCLE_RIGHT,
    ARROW_CIRCLE_UP,
    ARROW_DOWN,
    ARROW_LEFT,
    ARROW_RIGHT,
    ARROW_UP,
    ARROWS_ALT,

    CARET_DOWN,
    CARET_LEFT,
    CARET_RIGHT,

    CARET_UP,
    CHEVRON_CIRCLE_DOWN,
    CHEVRON_CIRCLE_LEFT,
    CHEVRON_CIRCLE_RIGHT,
    CHEVRON_CIRCLE_UP,
    CHEVRON_DOWN,
    CHEVRON_LEFT,
    CHEVRON_RIGHT,
    CHEVRON_UP,
    LONG_ARROW_DOWN,
    LONG_ARROW_LEFT,
    LONG_ARROW_RIGHT,
    LONG_ARROW_UP,

    BACKWARD,
    COMPRESS,
    EJECT,
    EXPAND,
    FAST_BACKWARD,
    FAST_FORWARD,
    FORWARD,
    PAUSE,
    PAUSE_CIRCLE,
    PAUSE_CIRCLE_O,
    PLAY,
    PLAY_CIRCLE,
    PLAY_CIRCLE_O,
    STEP_BACKWARD,
    STEP_FORWARD,
    STOP,
    STOP_CIRCLE,
    STOP_CIRCLE_O,
    YOUTUBE_PLAY;


    public static EnumLookUp<FontAwesome> lookUp = new EnumLookUp<>(FontAwesome.values());


    static final String BASE_STYLE = "fa";
    private String style;

    FontAwesome() {
        this.style = toString().toLowerCase();
    }

    FontAwesome(String style) {
        this.style = style;
    }

    public String getStyle() {
        return BASE_STYLE + " " + BASE_STYLE + "-" + replace(this.style);
    }

    public String replace(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '_') {
                result += '-';
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    public HTMLElement getIcon() {
        HTMLElement i = DomUtil.createI();
        i.className = getStyle();
        return i;
    }

}
