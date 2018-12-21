package com.williamlu.gankio

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

/**
 * @Author: WilliamLu
 * @Data: 2018/12/19
 * @Description:
 */
class MyTinkerApplation : TinkerApplication(
        ShareConstants.TINKER_ENABLE_ALL,
        AppConstant.ConfigConstant.TINKER_NAME,
        "com.tencent.tinker.loader.TinkerLoader",
        false)