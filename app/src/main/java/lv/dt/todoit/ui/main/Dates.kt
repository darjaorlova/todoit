package lv.dt.todoit.ui.main

fun Int.formatWithZero() = if (this > 9) this.toString() else "0$this"