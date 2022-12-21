package y2022

import DayX

class Day07 : DayX<Int>(95_437, 24_933_642) {

    override fun part1(input: List<String>): Int {
        val rootDir = Dir("/")
        initDir(input, rootDir)

        return getFoldersSize(rootDir)
            .filter { it.key <= 100_000 }
            .keys
            .sum()
    }

    override fun part2(input: List<String>): Int {
        val rootDir = Dir("/")
        initDir(input, rootDir)

        val freeSpace = 70_000_000 - rootDir.getSize()
        val requiredSpace = 30_000_000

        return getFoldersSize(rootDir)
            .keys
            .filter { it >= requiredSpace - freeSpace }
            .min()
    }

    private fun getNextCommandLineIndex(
        input: List<String>,
        startLineIndex: Int
    ): Int {
        return input
            .drop(startLineIndex)
            .indexOfFirst { it.startsWith("$") }
            .let {
                if (it == -1) {
                    input.size - 1
                } else {
                    it + startLineIndex
                }
            }
    }

    private fun initDir(
        input: List<String>,
        currentDir: Dir,
        startLineIndex: Int = 0,
    ) {
        val nextCommandIndex = with(getNextCommandLineIndex(input, startLineIndex) + 1) {
            if (this >= input.size) {
                input.size
            } else {
                this
            }
        }

        input.subList(startLineIndex, nextCommandIndex)
            .forEachIndexed { index, line ->
                when {
                    line == "$ cd /" -> {
                        var rootDir = currentDir
                        while (rootDir.parentDir != null) {
                            rootDir = rootDir.parentDir!!
                        }

                        initDir(input, rootDir, (startLineIndex + index + 1))
                    }

                    line == "$ cd .." -> {
                        initDir(input, currentDir.parentDir!!, (startLineIndex + index + 1))
                    }

                    line.startsWith("$ cd") -> {
                        val subFolderName = line.substring(5)

                        val subFolder = currentDir.subDirs
                            .first { it.name == subFolderName }

                        initDir(input, subFolder, (startLineIndex + index + 1))
                    }

                    line == "$ ls" -> {
                        initDir(input, currentDir, startLineIndex + index + 1)
                    }

                    else -> {
                        if (line.startsWith("dir")) {
                            Dir(
                                name = line.substring(4),
                                parentDir = currentDir
                            ).let {
                                currentDir.subDirs.add(it)
                            }
                        } else {
                            line.split(" ")
                                .let {
                                    File(
                                        name = it[1],
                                        size = it[0].toInt()
                                    )
                                }
                                .let {
                                    currentDir.files.add(it)
                                }

                        }
                    }
                }
            }
    }

    private fun getFoldersSize(currentDir: Dir): Map<Int, Dir> {
        val foldersSize: MutableMap<Int, Dir> = mutableMapOf()

        foldersSize[currentDir.getSize()] = currentDir

        currentDir.subDirs
            .forEach {
                foldersSize.putAll(getFoldersSize(it))
            }

        return foldersSize
    }

    private data class Dir(
        var name: String,
        var parentDir: Dir? = null,
        var subDirs: MutableList<Dir> = mutableListOf(),
        var files: MutableList<File> = mutableListOf()
    ) {

        fun getSize(): Int =
            subDirs.sumOf { it.getSize() } + files.sumOf { it.size }

        override fun toString(): String =
            name
    }

    private data class File(
        val name: String,
        val size: Int
    ) {

        override fun toString(): String =
            name
    }

}
