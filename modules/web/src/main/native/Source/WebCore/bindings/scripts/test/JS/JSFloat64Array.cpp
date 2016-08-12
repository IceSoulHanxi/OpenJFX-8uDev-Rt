/*
    This file is part of the WebKit open source project.
    This file has been generated by generate-bindings.pl. DO NOT MODIFY!

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Library General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Library General Public License for more details.

    You should have received a copy of the GNU Library General Public License
    along with this library; see the file COPYING.LIB.  If not, write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA 02110-1301, USA.
*/

#include "config.h"
#include "JSFloat64Array.h"

#include "ExceptionCode.h"
#include "JSArrayBufferViewHelper.h"
#include "JSDOMBinding.h"
#include "JSFloat32Array.h"
#include "JSInt32Array.h"
#include <runtime/Error.h>
#include <runtime/Float64Array.h>
#include <runtime/Int32Array.h>
#include <runtime/PropertyNameArray.h>
#include <wtf/GetPtr.h>

using namespace JSC;

namespace WebCore {

/* Hash table */

static const HashTableValue JSFloat64ArrayTableValues[] =
{
    { "constructor", DontEnum | ReadOnly, (intptr_t)static_cast<PropertySlot::GetValueFunc>(jsFloat64ArrayConstructor), (intptr_t)0, NoIntrinsic },
    { 0, 0, 0, 0, NoIntrinsic }
};

static const HashTable JSFloat64ArrayTable = { 2, 1, JSFloat64ArrayTableValues, 0 };
/* Hash table for constructor */

static const HashTableValue JSFloat64ArrayConstructorTableValues[] =
{
    { 0, 0, 0, 0, NoIntrinsic }
};

static const HashTable JSFloat64ArrayConstructorTable = { 1, 0, JSFloat64ArrayConstructorTableValues, 0 };
EncodedJSValue JSC_HOST_CALL JSFloat64ArrayConstructor::constructJSFloat64Array(ExecState* exec)
{
    JSFloat64ArrayConstructor* jsConstructor = jsCast<JSFloat64ArrayConstructor*>(exec->callee());
    RefPtr<Float64Array> array = constructArrayBufferView<Float64Array, double>(exec);
    if (!array.get())
        // Exception has already been thrown.
        return JSValue::encode(JSValue());
    return JSValue::encode(asObject(toJS(exec, jsConstructor->globalObject(), array.get())));
}

JSC::JSValue toJS(JSC::ExecState* exec, JSDOMGlobalObject* globalObject, Float64Array* object)
{
    return toJSArrayBufferView<JSFloat64Array>(exec, globalObject, object);
}

void JSFloat64Array::indexSetter(JSC::ExecState* exec, unsigned index, JSC::JSValue value)
{
    impl()->set(index, value.toNumber(exec));
}

static const HashTable* getJSFloat64ArrayConstructorTable(ExecState* exec)
{
    return getHashTableForGlobalData(exec->vm(), &JSFloat64ArrayConstructorTable);
}

const ClassInfo JSFloat64ArrayConstructor::s_info = { "Float64ArrayConstructor", &Base::s_info, 0, getJSFloat64ArrayConstructorTable, CREATE_METHOD_TABLE(JSFloat64ArrayConstructor) };

JSFloat64ArrayConstructor::JSFloat64ArrayConstructor(Structure* structure, JSDOMGlobalObject* globalObject)
    : DOMConstructorObject(structure, globalObject)
{
}

void JSFloat64ArrayConstructor::finishCreation(ExecState* exec, JSDOMGlobalObject* globalObject)
{
    Base::finishCreation(exec->vm());
    ASSERT(inherits(info()));
    putDirect(exec->vm(), exec->propertyNames().prototype, JSFloat64ArrayPrototype::self(exec, globalObject), DontDelete | ReadOnly);
    putDirect(exec->vm(), exec->propertyNames().length, jsNumber(1), ReadOnly | DontDelete | DontEnum);
}

bool JSFloat64ArrayConstructor::getOwnPropertySlot(JSObject* object, ExecState* exec, PropertyName propertyName, PropertySlot& slot)
{
    return getStaticValueSlot<JSFloat64ArrayConstructor, JSDOMWrapper>(exec, getJSFloat64ArrayConstructorTable(exec), jsCast<JSFloat64ArrayConstructor*>(object), propertyName, slot);
}

bool JSFloat64ArrayConstructor::getOwnPropertyDescriptor(JSObject* object, ExecState* exec, PropertyName propertyName, PropertyDescriptor& descriptor)
{
    return getStaticValueDescriptor<JSFloat64ArrayConstructor, JSDOMWrapper>(exec, getJSFloat64ArrayConstructorTable(exec), jsCast<JSFloat64ArrayConstructor*>(object), propertyName, descriptor);
}

ConstructType JSFloat64ArrayConstructor::getConstructData(JSCell*, ConstructData& constructData)
{
    constructData.native.function = constructJSFloat64Array;
    return ConstructTypeHost;
}

/* Hash table for prototype */

static const HashTableValue JSFloat64ArrayPrototypeTableValues[] =
{
    { "foo", DontDelete | JSC::Function, (intptr_t)static_cast<NativeFunction>(jsFloat64ArrayPrototypeFunctionFoo), (intptr_t)1, NoIntrinsic },
    { "set", DontDelete | JSC::Function, (intptr_t)static_cast<NativeFunction>(jsFloat64ArrayPrototypeFunctionSet), (intptr_t)0, NoIntrinsic },
    { 0, 0, 0, 0, NoIntrinsic }
};

static const HashTable JSFloat64ArrayPrototypeTable = { 4, 3, JSFloat64ArrayPrototypeTableValues, 0 };
static const HashTable* getJSFloat64ArrayPrototypeTable(ExecState* exec)
{
    return getHashTableForGlobalData(exec->vm(), &JSFloat64ArrayPrototypeTable);
}

const ClassInfo JSFloat64ArrayPrototype::s_info = { "Float64ArrayPrototype", &Base::s_info, 0, getJSFloat64ArrayPrototypeTable, CREATE_METHOD_TABLE(JSFloat64ArrayPrototype) };

JSObject* JSFloat64ArrayPrototype::self(ExecState* exec, JSGlobalObject* globalObject)
{
    return getDOMPrototype<JSFloat64Array>(exec, globalObject);
}

bool JSFloat64ArrayPrototype::getOwnPropertySlot(JSObject* object, ExecState* exec, PropertyName propertyName, PropertySlot& slot)
{
    JSFloat64ArrayPrototype* thisObject = jsCast<JSFloat64ArrayPrototype*>(object);
    return getStaticFunctionSlot<JSObject>(exec, getJSFloat64ArrayPrototypeTable(exec), thisObject, propertyName, slot);
}

bool JSFloat64ArrayPrototype::getOwnPropertyDescriptor(JSObject* object, ExecState* exec, PropertyName propertyName, PropertyDescriptor& descriptor)
{
    JSFloat64ArrayPrototype* thisObject = jsCast<JSFloat64ArrayPrototype*>(object);
    return getStaticFunctionDescriptor<JSObject>(exec, getJSFloat64ArrayPrototypeTable(exec), thisObject, propertyName, descriptor);
}

static const HashTable* getJSFloat64ArrayTable(ExecState* exec)
{
    return getHashTableForGlobalData(exec->vm(), &JSFloat64ArrayTable);
}

const ClassInfo JSFloat64Array::s_info = { "Float64Array", &Base::s_info, 0, getJSFloat64ArrayTable , CREATE_METHOD_TABLE(JSFloat64Array) };

JSFloat64Array::JSFloat64Array(Structure* structure, JSDOMGlobalObject* globalObject, PassRefPtr<Float64Array> impl)
    : JSArrayBufferView(structure, globalObject, impl)
{
}

void JSFloat64Array::finishCreation(VM& vm)
{
    Base::finishCreation(vm);
    TypedArrayDescriptor descriptor(JSFloat64Array::info(), OBJECT_OFFSETOF(JSFloat64Array, m_storage), OBJECT_OFFSETOF(JSFloat64Array, m_storageLength));
    vm.registerTypedArrayDescriptor(impl(), descriptor);
    m_storage = impl()->data();
    m_storageLength = impl()->length();
    ASSERT(inherits(info()));
}

JSObject* JSFloat64Array::createPrototype(ExecState* exec, JSGlobalObject* globalObject)
{
    return JSFloat64ArrayPrototype::create(exec->vm(), globalObject, JSFloat64ArrayPrototype::createStructure(exec->vm(), globalObject, JSArrayBufferViewPrototype::self(exec, globalObject)));
}

bool JSFloat64Array::getOwnPropertySlot(JSObject* object, ExecState* exec, PropertyName propertyName, PropertySlot& slot)
{
    JSFloat64Array* thisObject = jsCast<JSFloat64Array*>(object);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());
    Optional<uint32_t> index = parseIndex(propertyName);
    if (index && index.value() < static_cast<Float64Array*>(thisObject->impl())->length()) {
        slot.setValue(thisObject, thisObject->getByIndex(exec, index.value()));
        return true;
    }
    return getStaticValueSlot<JSFloat64Array, Base>(exec, getJSFloat64ArrayTable(exec), thisObject, propertyName, slot);
}

bool JSFloat64Array::getOwnPropertyDescriptor(JSObject* object, ExecState* exec, PropertyName propertyName, PropertyDescriptor& descriptor)
{
    JSFloat64Array* thisObject = jsCast<JSFloat64Array*>(object);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());
    Optional<uint32_t> index = parseIndex(propertyName);
    if (index && index.value() < static_cast<Float64Array*>(thisObject->impl())->length()) {
        descriptor.setDescriptor(thisObject->getByIndex(exec, index.value()), DontDelete);
        return true;
    }
    return getStaticValueDescriptor<JSFloat64Array, Base>(exec, getJSFloat64ArrayTable(exec), thisObject, propertyName, descriptor);
}

bool JSFloat64Array::getOwnPropertySlotByIndex(JSObject* object, ExecState* exec, unsigned index, PropertySlot& slot)
{
    JSFloat64Array* thisObject = jsCast<JSFloat64Array*>(object);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());
    if (index < static_cast<Float64Array*>(thisObject->impl())->length()) {
        slot.setValue(thisObject, thisObject->getByIndex(exec, index));
        return true;
    }
    return Base::getOwnPropertySlotByIndex(thisObject, exec, index, slot);
}

JSValue jsFloat64ArrayConstructor(ExecState* exec, JSValue slotBase, PropertyName)
{
    JSFloat64Array* domObject = jsCast<JSFloat64Array*>(asObject(slotBase));
    return JSFloat64Array::getConstructor(exec, domObject->globalObject());
}

void JSFloat64Array::put(JSCell* cell, ExecState* exec, PropertyName propertyName, JSValue value, PutPropertySlot& slot)
{
    JSFloat64Array* thisObject = jsCast<JSFloat64Array*>(cell);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());
    if (Optional<uint32_t> index = parseIndex(propertyName)) {
        thisObject->indexSetter(exec, index.value(), value);
        return;
    }
    Base::put(thisObject, exec, propertyName, value, slot);
}

void JSFloat64Array::putByIndex(JSCell* cell, ExecState* exec, unsigned index, JSValue value, bool shouldThrow)
{
    JSFloat64Array* thisObject = jsCast<JSFloat64Array*>(cell);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());
    if (index <= MAX_ARRAY_INDEX) {
        UNUSED_PARAM(shouldThrow);
        thisObject->indexSetter(exec, index, value);
        return;
    }
    Base::putByIndex(cell, exec, index, value, shouldThrow);
}

void JSFloat64Array::getOwnPropertyNames(JSObject* object, ExecState* exec, PropertyNameArray& propertyNames, EnumerationMode mode)
{
    JSFloat64Array* thisObject = jsCast<JSFloat64Array*>(object);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());
    for (unsigned i = 0; i < static_cast<Float64Array*>(thisObject->impl())->length(); ++i)
        propertyNames.add(Identifier::from(exec, i));
    Base::getOwnPropertyNames(thisObject, exec, propertyNames, mode);
}

JSValue JSFloat64Array::getConstructor(ExecState* exec, JSGlobalObject* globalObject)
{
    return getDOMConstructor<JSFloat64ArrayConstructor>(exec, jsCast<JSDOMGlobalObject*>(globalObject));
}

EncodedJSValue JSC_HOST_CALL jsFloat64ArrayPrototypeFunctionFoo(ExecState* exec)
{
    JSValue thisValue = exec->thisValue();
    if (!thisValue.inherits(JSFloat64Array::info()))
        return throwVMTypeError(exec);
    JSFloat64Array* castedThis = jsCast<JSFloat64Array*>(asObject(thisValue));
    ASSERT_GC_OBJECT_INHERITS(castedThis, JSFloat64Array::info());
    Float64Array* impl = static_cast<Float64Array*>(castedThis->impl());
    if (exec->argumentCount() < 1)
        return throwVMError(exec, createNotEnoughArgumentsError(exec));
    Float32Array* array(toFloat32Array(exec->argument(0)));
    if (exec->hadException())
        return JSValue::encode(jsUndefined());

    JSC::JSValue result = toJS(exec, castedThis->globalObject(), WTF::getPtr(impl->foo(array)));
    return JSValue::encode(result);
}

EncodedJSValue JSC_HOST_CALL jsFloat64ArrayPrototypeFunctionSet(ExecState* exec)
{
    JSValue thisValue = exec->thisValue();
    if (!thisValue.inherits(JSFloat64Array::info()))
        return throwVMTypeError(exec);
    JSFloat64Array* castedThis = jsCast<JSFloat64Array*>(asObject(thisValue));
    ASSERT_GC_OBJECT_INHERITS(castedThis, JSFloat64Array::info());
    return JSValue::encode(setWebGLArrayHelper<Float64Array, double>(exec, castedThis->impl()));
}


JSValue JSFloat64Array::getByIndex(ExecState*, unsigned index)
{
    ASSERT_GC_OBJECT_INHERITS(this, info());
    double result = static_cast<Float64Array*>(impl())->item(index);
    if (std::isnan(result))
        return jsNaN();
    return JSValue(result);
}

Float64Array* toFloat64Array(JSC::JSValue value)
{
    return value.inherits(JSFloat64Array::info()) ? jsCast<JSFloat64Array*>(asObject(value))->impl() : 0;
}

}
