package com.metis.downloader.file

object Const {
  private const val MAX_NUM = 4
  private const val MIN_NUM = 0

  const val TEMP_USER = "admin"
  const val TEMP_PASS = "14562335"
  const val OUTPUT_PERCENT = "out_percent"
  const val INPUT_URL = "input_url"
  const val INPUT_NAME = "input_name"
  const val INPUT_ID = "input_id"
  const val OUTPUT_FILE_PATH = "output_file_path"
  const val EXTRA_FILE_PATH = "extra_file_path"
  const val DOWNLOAD_DIR = "/Download/"

  val videoLinks: List<String> = listOf(
    "https://www.sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4",
    "https://www.sample-videos.com/video123/mp4/720/big_buck_bunny_720p_2mb.mp4",
    "https://www.sample-videos.com/video123/mp4/720/big_buck_bunny_720p_5mb.mp4",
    "https://www.sample-videos.com/video123/mp4/720/big_buck_bunny_720p_10mb.mp4",
    "https://www.sample-videos.com/video123/mp4/720/big_buck_bunny_720p_20mb.mp4"
  )
  val imageLinks: List<String> = listOf(
    "https://www.cambridgema.gov/~/media/Images/sharedphotos/departmentphotos/animal.jpg?mw=1920",
    "https://www.artfido.com/wp-content/uploads/2013/09/amazing-animal-pictures-37.jpg",
    "https://www.artfido.com/wp-content/uploads/2013/09/amazing-animal-pictures-35.jpg",
    "https://www.artfido.com/wp-content/uploads/2013/09/amazing-animal-pictures-32.jpg",
    "https://www.artfido.com/wp-content/uploads/2013/09/amazing-animal-pictures-3.jpg"
  )

  fun getRandomBetweenRange(): Int {
    return (Math.random() * (MAX_NUM - MIN_NUM + 1) + 0).toInt()
  }
}
