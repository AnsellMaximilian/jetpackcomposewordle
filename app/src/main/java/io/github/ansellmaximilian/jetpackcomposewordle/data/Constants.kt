package io.github.ansellmaximilian.jetpackcomposewordle.data

const val MAX_WORD_LENGTH = 5
const val MAX_CHANCES = 6

val words: Set<String> = setOf(
    "apple", "brave", "charm", "drown", "eager", "frost", "grasp", "humor", "ivory", "jolly",
    "knack", "lemon", "mirth", "noble", "ocean", "pride", "quilt", "rider", "spine", "tiger",
    "uncle", "vivid", "whale", "xenon", "youth", "zebra", "adept", "bloom", "crisp", "drift",
    "event", "flame", "globe", "haste", "image", "jumps", "karma", "lapse", "mango", "nerve",
    "orbit", "petal", "quiet", "raven", "skirt", "twist", "upset", "vocal", "woven", "xerox",
    "yield", "zesty", "amber", "bison", "cider", "daisy", "ember", "fjord", "grime", "haste",
    "input", "jazzy", "knots", "latch", "march", "nudge", "optic", "plumb", "quark", "radar",
    "sneak", "tough", "ultra", "vigor", "waltz", "merit", "yummy", "zonal", "acorn", "blaze",
    "clash", "dizzy", "elite", "flock", "glyph", "hover", "ivied", "joked", "knobs", "lucky",
    "motto", "novel", "omega", "piano", "quirk", "relic", "shiny", "trend", "unity", "vexed"
)

enum class Correctness {
    INCORRECT,
    PLACEMENT,
    CORRECT
}