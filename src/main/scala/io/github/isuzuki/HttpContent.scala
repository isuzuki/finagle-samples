package io.github.isuzuki

import java.util.Base64

import com.twitter.io.Buf

object HttpContent {
  lazy val gif1px: Buf = Buf.ByteArray.Owned(
    Base64.getDecoder.decode("R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==")
  )
}
