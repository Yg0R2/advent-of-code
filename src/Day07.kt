fun main() {

    fun getNextCommandLineIndex(
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

    fun initDir(
        input: List<String>,
        currentDir: Dir,
        startLineIndex: Int = 0,
    ) {
        val nextCommandIndex = with (getNextCommandLineIndex(input, startLineIndex) + 1) {
            if (this >= input.size) {
                input.size
            } else {
                this
            }
        }

        input
            .subList(startLineIndex, nextCommandIndex)
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

    fun getFoldersSize(currentDir: Dir): Map<Int, Dir> {
        val foldersSize: MutableMap<Int, Dir> = mutableMapOf()

        foldersSize[currentDir.getSize()] = currentDir

        currentDir.subDirs
            .forEach {
                foldersSize.putAll(getFoldersSize(it))
            }

        return foldersSize
    }

    fun part1(input: List<String>): Int {
        val rootDir = Dir("/")
        initDir(input, rootDir)

        return getFoldersSize(rootDir)
            .filter { it.key <= 100_000 }
            .keys
            .sum()
    }

    fun part2(input: List<String>): Int {
        val rootDir = Dir("/")
        initDir(input, rootDir)

        val freeSpace = 70_000_000 - rootDir.getSize()
        val requiredSpace = 30_000_000

        return getFoldersSize(rootDir)
            .keys
            .filter { it >= requiredSpace - freeSpace }
            .min()
    }

    // test if implementation meets criteria from the description, like:
    with("Day07") {
        val testInput = readInput("${this}_test")
        checkResult("test part1", 95437) {
            part1(testInput)
        }
        checkResult("test part2", 24933642) {
            part2(testInput)
        }

        val input = readInput(this)
        println(part1(input))
        println(part2(input))
    }

}

data class Dir(
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

data class File(
    val name: String,
    val size: Int
) {

    override fun toString(): String =
        name
}
