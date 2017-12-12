/*
 * Copyright (C) 1999 Lars Knoll (knoll@kde.org)
 *           (C) 1999 Antti Koivisto (koivisto@kde.org)
 *           (C) 2001 Dirk Mueller (mueller@kde.org)
 * Copyright (C) 2003, 2010 Apple Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public License
 * along with this library; see the file COPYING.LIB.  If not, write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301, USA.
 */

#include "config.h"
#include "HTMLTitleElement.h"

#include "Document.h"
#include "HTMLNames.h"
#include "NodeRenderStyle.h"
#include "RenderElement.h"
#include "RenderStyle.h"
#include "StyleInheritedData.h"
#include "StyleResolver.h"
#include "Text.h"
#include "TextNodeTraversal.h"
#include <wtf/Ref.h>
#include <wtf/text/StringBuilder.h>

namespace WebCore {

using namespace HTMLNames;

inline HTMLTitleElement::HTMLTitleElement(const QualifiedName& tagName, Document& document)
    : HTMLElement(tagName, document)
{
    ASSERT(hasTagName(titleTag));
}

Ref<HTMLTitleElement> HTMLTitleElement::create(const QualifiedName& tagName, Document& document)
{
    return adoptRef(*new HTMLTitleElement(tagName, document));
}

Node::InsertionNotificationRequest HTMLTitleElement::insertedInto(ContainerNode& insertionPoint)
{
    HTMLElement::insertedInto(insertionPoint);
    if (isConnected() && !isInShadowTree())
        document().titleElementAdded(*this);
    return InsertionDone;
}

void HTMLTitleElement::removedFrom(ContainerNode& insertionPoint)
{
    HTMLElement::removedFrom(insertionPoint);
    if (insertionPoint.isConnected() && !insertionPoint.isInShadowTree())
        document().titleElementRemoved(*this);
}

void HTMLTitleElement::childrenChanged(const ChildChange& change)
{
    HTMLElement::childrenChanged(change);
    m_title = computedTextWithDirection();
    document().titleElementTextChanged(*this);
}

String HTMLTitleElement::text() const
{
    return TextNodeTraversal::childTextContent(*this);
}

StringWithDirection HTMLTitleElement::computedTextWithDirection()
{
    TextDirection direction = LTR;
    if (auto* computedStyle = this->computedStyle())
        direction = computedStyle->direction();
    else
        direction = styleResolver().styleForElement(*this, parentElement() ? parentElement()->renderStyle() : nullptr).renderStyle->direction();
    return { text(), direction };
}

void HTMLTitleElement::setText(const String& value)
{
    setTextContent(value);
}

}
