/*
 * Copyright (C) 2015 Apple Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY APPLE INC. AND ITS CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL APPLE INC. OR ITS CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

#ifndef HTMLTableHeaderCellElement_h
#define HTMLTableHeaderCellElement_h

#include "HTMLNames.h"
#include "HTMLTableCellElement.h"

namespace WebCore {

class HTMLTableHeaderCellElement final : public HTMLTableCellElement {
public:
    static Ref<HTMLTableHeaderCellElement> create(Document& document)
    {
        return adoptRef(*new HTMLTableHeaderCellElement(HTMLNames::thTag, document));
    }

    static Ref<HTMLTableHeaderCellElement> create(const QualifiedName& tagName, Document& document)
    {
        ASSERT(tagName == HTMLNames::thTag);
        return adoptRef(*new HTMLTableHeaderCellElement(tagName, document));
    }

    const AtomicString& scope() const;
    void setScope(const AtomicString&);

private:
#if ENABLE(CXX_11_FIX)
    HTMLTableHeaderCellElement(const QualifiedName& tagName, Document& document)
        : HTMLTableCellElement(tagName, document) {}
#else
    using HTMLTableCellElement::HTMLTableCellElement;
#endif
};

}

#endif // HTMLTableHeaderCellElement_h

