#
# Makefile for Linux only avplugin
#

BUILD_DIR = $(OUTPUT_DIR)/$(BUILD_TYPE)
TARGET_DIRS = $(BUILD_DIR)

BASE_DIR = ../../..

GLIB_VERSION = 2.56.1
GLIB_DIR = $(BASE_DIR)/3rd_party/glib/glib-$(GLIB_VERSION)

SRCBASE_DIR = ../../../plugins

DIRLIST = av

ifneq ($(strip $(VERSION)),)
TARGET = $(BUILD_DIR)/lib$(BASE_NAME)$(SUFFIX)-$(VERSION).so
OBJBASE_DIR = $(BUILD_DIR)/obj/plugins/$(BASE_NAME)$(SUFFIX)-$(VERSION)
else
TARGET = $(BUILD_DIR)/lib$(BASE_NAME).so
OBJBASE_DIR = $(BUILD_DIR)/obj/plugins/avplugin
endif

CFLAGS = -fPIC                   \
         -Wformat                \
         -Wextra                 \
         -Wformat-security       \
         -Werror=implicit-function-declaration \
         -fstack-protector       \
         -msse2                  \
         -fbuiltin               \
         -DHAVE_STDINT_H         \
         -DLINUX                 \
         -DGST_DISABLE_LOADSAVE  \
         -DGSTREAMER_LITE \
         -DG_DISABLE_DEPRECATED \
         -ffunction-sections -fdata-sections

ifeq ($(BUILD_TYPE), Release)
    CFLAGS += -Os
else
    CFLAGS += -g -Wall
endif

INCLUDES= -I../../../plugins                          \
          -I../../../plugins/av                       \
          -I../../../gstreamer-lite/gstreamer         \
          -I../../../gstreamer-lite/gstreamer/libs    \
          -I$(GLIB_DIR)                               \
          -I$(GLIB_DIR)/glib                          \
          -I$(GLIB_DIR)/gmodule

LDFLAGS = -L$(BUILD_DIR)    \
          -z relro          \
          -lgstreamer-lite  \
          -lglib-lite  \
          -static-libgcc -static-libstdc++ -Wl,--gc-sections

ifneq ($(strip $(LIBAV_DIR)),)
INCLUDES += -I$(LIBAV_DIR)/include
LDFLAGS  += -L$(LIBAV_DIR)/lib/ -lavcodec -lavformat
else
INCLUDES += $(shell pkg-config --cflags libavcodec libavformat)
LDFLAGS  += $(shell pkg-config --libs libavcodec libavformat)
endif

ifeq ($(ARCH), x32)
    CFLAGS += -m32
    LDFLAGS += -m32
    INCLUDES += -I$(GLIB_DIR)/build/linux/x86
else
    INCLUDES += -I$(GLIB_DIR)/build/linux/x64
endif

SOURCES = av/fxavcodecplugin.c  \
          av/avelement.c        \
          av/decoder.c          \
          av/audiodecoder.c     \
          av/videodecoder.c     \
          av/mpegtsdemuxer.c

OBJ_DIRS = $(addprefix $(OBJBASE_DIR)/,$(DIRLIST))
OBJECTS = $(patsubst %.c,$(OBJBASE_DIR)/%.o,$(SOURCES))

.PHONY: default

default: $(TARGET)

$(OBJBASE_DIR)/%.o: $(SRCBASE_DIR)/%.c
	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

$(OBJECTS): | $(OBJ_DIRS) $(TARGET_DIRS)

$(OBJ_DIRS):
	mkdir -p $(OBJ_DIRS)

$(TARGET_DIRS):
	mkdir -p $(TARGET_DIRS)

$(TARGET): $(OBJECTS)
	$(CC) -shared $(OBJECTS) $(LDFLAGS) -o $@
