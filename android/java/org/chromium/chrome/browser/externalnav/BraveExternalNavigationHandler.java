/* Copyright (c) 2020 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.chromium.chrome.browser.externalnav;

import org.chromium.components.external_intents.ExternalNavigationDelegate;
import org.chromium.components.external_intents.ExternalNavigationHandler;
import org.chromium.components.external_intents.ExternalNavigationParams;
import org.chromium.components.external_intents.ExternalNavigationHandler.OverrideUrlLoadingResult;
import org.chromium.chrome.browser.tab.Tab;
import org.chromium.chrome.browser.BraveUphold;

public class BraveExternalNavigationHandler extends ExternalNavigationHandler {
    private BraveUphold mBraveUphold;

    public BraveExternalNavigationHandler(ExternalNavigationDelegate delegate) {
        super(delegate);
    }

    @Override
    public @OverrideUrlLoadingResult int shouldOverrideUrlLoading(ExternalNavigationParams params) {
        if (isUpholdOverride(params)) {
            CompleteUpholdVerification(params);
            return OverrideUrlLoadingResult.OVERRIDE_WITH_CLOBBERING_TAB;
        }
        return super.shouldOverrideUrlLoading(params);
    }

    private boolean isUpholdOverride(ExternalNavigationParams params) {
        if (!params.getUrl().startsWith(BraveUphold.UPHOLD_REDIRECT_URL)) return false;
        return true;
    }

    private void CompleteUpholdVerification(ExternalNavigationParams params) {
        mBraveUphold = new BraveUphold();
        mBraveUphold.CompleteUpholdVerification(params, this);
    }

    /**
     * This function will be removed by ASM byte-code patching tool
     * and redirected to ExternalNavigationHandler.
     */
    public @OverrideUrlLoadingResult int clobberCurrentTabWithFallbackUrl(
            String browserFallbackUrl, ExternalNavigationParams params) {
        assert false;
        return OverrideUrlLoadingResult.OVERRIDE_WITH_CLOBBERING_TAB;
    }
}