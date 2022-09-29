package com.willor.albertsons_interview.mainactivityviewmodel_test

import com.willor.lib_data.domain.dataobjects.AcromineResp
import com.willor.lib_data.domain.dataobjects.AcromineRespItem
import com.willor.lib_data.domain.dataobjects.LongFormWithVariations
import com.willor.lib_data.domain.dataobjects.Variation




val acromineRespItemDummy = AcromineRespItem(
    shortForm = "HMM",
    longFormWithVariations = listOf(
        LongFormWithVariations(
            freq = 1,
            longForm = "Hello Mad Max",
            since = 1999,
            variations = listOf(
                Variation(
                    freq = 12,
                    longForm = "hello mad max",
                    usedSince = 1999
                )
            )
        )
    )
)

private val resp = AcromineResp().apply {
    this.add(acromineRespItemDummy)
}

val dummyData = resp

