package org.ice1000.dekoder.utils.threads

import org.ice1000.dekoder.data.PlayData
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.SourceDataLine

/**
 * @author ice1000
 * Created by asus1 on 2016/7/1.
 */

class PlayerRunSupporter {

	companion object CONSTANTS {
		const val BUFFER_SIZE = 0x10000
	}

	fun run(playData: PlayData, ais: AudioInputStream, line: SourceDataLine) {
		try {
			line.open()
		} catch (e: Exception) {
		}
		line.start()
		var inBytes = 0
		var cnt = 0
		while (inBytes != -1 && !playData.threadExit)
			if (!playData.isPaused) {
				if (0 == cnt % 100) println("loop ${cnt++}")
				val audioData = ByteArray(BUFFER_SIZE)
				inBytes = ais.read(audioData, 0, BUFFER_SIZE)
				if (inBytes >= 0)
					line.write(audioData, 0, inBytes)
			}
		line.drain()
		line.close()
		println("Ended playing")
	}
}