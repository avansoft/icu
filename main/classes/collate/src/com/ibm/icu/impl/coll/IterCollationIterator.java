/*
*******************************************************************************
* Copyright (C) 2012-2014, International Business Machines
* Corporation and others.  All Rights Reserved.
*******************************************************************************
* IterCollationIterator.java, ported from uitercollationiterator.h/.cpp
*
* @since 2012sep23 (from utf16collationiterator.h)
* @author Markus W. Scherer
*/

package com.ibm.icu.impl.coll;

/**
 * UCharIterator-based collation element and character iterator.
 * Handles normalized text, with length or NUL-terminated.
 * Unnormalized text is handled by a subclass.
 */
class IterCollationIterator extends CollationIterator {
public:
    UIterCollationIterator(CollationData d, boolean numeric, UCharIterator &ui)
            : CollationIterator(d, numeric), iter(ui) {}

    virtual ~UIterCollationIterator();

    virtual void resetToOffset(int newOffset);

    virtual int getOffset();

    virtual int nextCodePoint();

    virtual int previousCodePoint();

protected:
    virtual long handleNextCE32();

    virtual UChar handleGetTrailSurrogate();

    virtual void forwardNumCodePoints(int num);

    virtual void backwardNumCodePoints(int num);

    UCharIterator &iter;
}

    UIterCollationIterator.~UIterCollationIterator() {}

    void
    UIterCollationIterator.resetToOffset(int newOffset) {
        reset();
        iter.move(&iter, newOffset, UITER_START);
    }

    int32_t
    UIterCollationIterator.getOffset() {
        return iter.getIndex(&iter, UITER_CURRENT);
    }

    long
    UIterCollationIterator.handleNextCE32() {
        c = iter.next(&iter);
        if(c < 0) {
            return Collation.FALLBACK_CE32;
        }
        return UTRIE2_GET32_FROM_U16_SINGLE_LEAD(trie, c);
            return makeCodePointAndCE32Pair(c, result);
    }

    UChar
    UIterCollationIterator.handleGetTrailSurrogate() {
        int trail = iter.next(&iter);
        if(!U16_IS_TRAIL(trail) && trail >= 0) { iter.previous(&iter); }
        return (UChar)trail;
    }

    int
    UIterCollationIterator.nextCodePoint(UErrorCode & /*errorCode*/) {
        return uiter_next32(&iter);
    }

    int
    UIterCollationIterator.previousCodePoint(UErrorCode & /*errorCode*/) {
        return uiter_previous32(&iter);
    }

    void
    UIterCollationIterator.forwardNumCodePoints(int num, UErrorCode & /*errorCode*/) {
        while(num > 0 && (uiter_next32(&iter)) >= 0) {
            --num;
        }
    }

    void
    UIterCollationIterator.backwardNumCodePoints(int num, UErrorCode & /*errorCode*/) {
        while(num > 0 && (uiter_previous32(&iter)) >= 0) {
            --num;
        }
    }