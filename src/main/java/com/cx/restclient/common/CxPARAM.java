package com.cx.restclient.common;

import java.io.File;

/**
 * Created by Galn on 14/02/2018.
 */
public abstract class CxPARAM {
    public static final String AUTHENTICATION = "auth/identity/connect/token";
    public static final String ORIGIN_HEADER = "cxOrigin";
    public static final String CXPRESETS = "sast/presets";
    public static final String CXTEAMS = "auth/teams";
    public static final String CREATE_PROJECT = "projects";//Create new project (default preset and configuration)


    public static final String OPTION_TRUE = "true";
    public static final String OPTION_FALSE = "false";

    public static final String CX_REPORT_LOCATION = File.separator + "Checkmarx" + File.separator + "Reports";

}
