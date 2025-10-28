package ink.terraria.bilitimelinedemo

import java.time.LocalDate
import kotlin.random.Random

class DataSender {
    fun createUpData(): MutableList<UpData> {
        val u1 = UpData("金正恩", Random.nextInt(1,Int.MAX_VALUE), R.drawable.up1)
        val u2 = UpData("特朗普", Random.nextInt(1,Int.MAX_VALUE), R.drawable.up2)
        val u3 = UpData("科比", Random.nextInt(1,Int.MAX_VALUE), R.drawable.up3)
        val u4 = UpData("永雏塔菲", Random.nextInt(1,Int.MAX_VALUE), R.drawable.up4)
        val u5 = UpData("Caigito", Random.nextInt(1,Int.MAX_VALUE), R.drawable.up5)
        return mutableListOf(u1, u2, u3, u4, u5)
    }

    fun createPostData(): MutableList<PostData> {
        val u1 = UpData("金正恩", Random.nextInt(1,Int.MAX_VALUE), R.drawable.up1)
        val u2 = UpData("特朗普", Random.nextInt(1,Int.MAX_VALUE), R.drawable.up2)
        val u3 = UpData("科比", Random.nextInt(1,Int.MAX_VALUE), R.drawable.up3)

        val p1 =
            PostData(u1, "让我看看，是谁又跳不动了", R.drawable.post1, LocalDate.parse("2025-10-28"))
        val p2 = PostData(
            u2,
            "\uD83D\uDE4C没有人\n" +
                    "\uD83D\uDC50比我\n" +
                    "\uD83D\uDC4C更懂\n" +
                    "☝\uFE0F拉手风琴",
            R.drawable.post2, LocalDate.parse("2025-01-20")
        )
        val p3 = PostData(u3, "What can I say?", R.drawable.post3, LocalDate.parse("2020-01-26"))

        return mutableListOf(p1, p2, p3)
    }


}
