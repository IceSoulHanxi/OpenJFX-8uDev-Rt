/*
 *  This file is part of the WebKit open source project.
 *  This file has been generated by generate-bindings.pl. DO NOT MODIFY!
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Library General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public License
 *  along with this library; see the file COPYING.LIB.  If not, write to
 *  the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 *  Boston, MA 02110-1301, USA.
 */

#include "config.h"
#include "WebKitDOMTestCustomNamedGetter.h"

#include "CSSImportRule.h"
#include "DOMObjectCache.h"
#include "Document.h"
#include "ExceptionCode.h"
#include "JSMainThreadExecState.h"
#include "WebKitDOMPrivate.h"
#include "WebKitDOMTestCustomNamedGetterPrivate.h"
#include "gobject/ConvertToUTF8String.h"
#include <wtf/GetPtr.h>
#include <wtf/RefPtr.h>

#define WEBKIT_DOM_TEST_CUSTOM_NAMED_GETTER_GET_PRIVATE(obj) G_TYPE_INSTANCE_GET_PRIVATE(obj, WEBKIT_TYPE_DOM_TEST_CUSTOM_NAMED_GETTER, WebKitDOMTestCustomNamedGetterPrivate)

typedef struct _WebKitDOMTestCustomNamedGetterPrivate {
    RefPtr<WebCore::TestCustomNamedGetter> coreObject;
} WebKitDOMTestCustomNamedGetterPrivate;

namespace WebKit {

WebKitDOMTestCustomNamedGetter* kit(WebCore::TestCustomNamedGetter* obj)
{
    if (!obj)
        return 0;

    if (gpointer ret = DOMObjectCache::get(obj))
        return WEBKIT_DOM_TEST_CUSTOM_NAMED_GETTER(ret);

    return wrapTestCustomNamedGetter(obj);
}

WebCore::TestCustomNamedGetter* core(WebKitDOMTestCustomNamedGetter* request)
{
    return request ? static_cast<WebCore::TestCustomNamedGetter*>(WEBKIT_DOM_OBJECT(request)->coreObject) : 0;
}

WebKitDOMTestCustomNamedGetter* wrapTestCustomNamedGetter(WebCore::TestCustomNamedGetter* coreObject)
{
    ASSERT(coreObject);
    return WEBKIT_DOM_TEST_CUSTOM_NAMED_GETTER(g_object_new(WEBKIT_TYPE_DOM_TEST_CUSTOM_NAMED_GETTER, "core-object", coreObject, NULL));
}

} // namespace WebKit

G_DEFINE_TYPE(WebKitDOMTestCustomNamedGetter, webkit_dom_test_custom_named_getter, WEBKIT_TYPE_DOM_OBJECT)

static void webkit_dom_test_custom_named_getter_finalize(GObject* object)
{
    WebKitDOMTestCustomNamedGetterPrivate* priv = WEBKIT_DOM_TEST_CUSTOM_NAMED_GETTER_GET_PRIVATE(object);

    WebKit::DOMObjectCache::forget(priv->coreObject.get());

    priv->~WebKitDOMTestCustomNamedGetterPrivate();
    G_OBJECT_CLASS(webkit_dom_test_custom_named_getter_parent_class)->finalize(object);
}

static GObject* webkit_dom_test_custom_named_getter_constructor(GType type, guint constructPropertiesCount, GObjectConstructParam* constructProperties)
{
    GObject* object = G_OBJECT_CLASS(webkit_dom_test_custom_named_getter_parent_class)->constructor(type, constructPropertiesCount, constructProperties);

    WebKitDOMTestCustomNamedGetterPrivate* priv = WEBKIT_DOM_TEST_CUSTOM_NAMED_GETTER_GET_PRIVATE(object);
    priv->coreObject = static_cast<WebCore::TestCustomNamedGetter*>(WEBKIT_DOM_OBJECT(object)->coreObject);
    WebKit::DOMObjectCache::put(priv->coreObject.get(), object);

    return object;
}

static void webkit_dom_test_custom_named_getter_class_init(WebKitDOMTestCustomNamedGetterClass* requestClass)
{
    GObjectClass* gobjectClass = G_OBJECT_CLASS(requestClass);
    g_type_class_add_private(gobjectClass, sizeof(WebKitDOMTestCustomNamedGetterPrivate));
    gobjectClass->constructor = webkit_dom_test_custom_named_getter_constructor;
    gobjectClass->finalize = webkit_dom_test_custom_named_getter_finalize;
}

static void webkit_dom_test_custom_named_getter_init(WebKitDOMTestCustomNamedGetter* request)
{
    WebKitDOMTestCustomNamedGetterPrivate* priv = WEBKIT_DOM_TEST_CUSTOM_NAMED_GETTER_GET_PRIVATE(request);
    new (priv) WebKitDOMTestCustomNamedGetterPrivate();
}

void webkit_dom_test_custom_named_getter_another_function(WebKitDOMTestCustomNamedGetter* self, const gchar* str)
{
    WebCore::JSMainThreadNullState state;
    g_return_if_fail(WEBKIT_DOM_IS_TEST_CUSTOM_NAMED_GETTER(self));
    g_return_if_fail(str);
    WebCore::TestCustomNamedGetter* item = WebKit::core(self);
    WTF::String convertedStr = WTF::String::fromUTF8(str);
    item->anotherFunction(convertedStr);
}

