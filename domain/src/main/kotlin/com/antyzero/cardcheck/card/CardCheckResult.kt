package com.antyzero.cardcheck.card

/**
 * We need result representation of our check
 */
enum class CardCheckResult {
    NotExpired, Expired, UnableToGetInformation;
}