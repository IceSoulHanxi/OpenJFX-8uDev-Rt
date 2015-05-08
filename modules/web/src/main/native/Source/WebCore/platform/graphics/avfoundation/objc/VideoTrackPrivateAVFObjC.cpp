/*
 * Copyright (C) 2013 Apple Inc. All rights reserved.
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

#import "config.h"
#import "VideoTrackPrivateAVFObjC.h"
#import "AVTrackPrivateAVFObjCImpl.h"

#if ENABLE(VIDEO_TRACK)

namespace WebCore {

VideoTrackPrivateAVFObjC::VideoTrackPrivateAVFObjC(AVPlayerItemTrack* track)
    : m_impl(std::make_unique<AVTrackPrivateAVFObjCImpl>(track))
{
    resetPropertiesFromTrack();
}

VideoTrackPrivateAVFObjC::VideoTrackPrivateAVFObjC(AVAssetTrack* track)
    : m_impl(std::make_unique<AVTrackPrivateAVFObjCImpl>(track))
{
    resetPropertiesFromTrack();
}

void VideoTrackPrivateAVFObjC::resetPropertiesFromTrack()
{
    setSelected(m_impl->enabled());
    setKind(m_impl->videoKind());
    setId(m_impl->id());
    setLabel(m_impl->label());
    setLanguage(m_impl->language());
}

void VideoTrackPrivateAVFObjC::setPlayerItemTrack(AVPlayerItemTrack *track)
{
    m_impl = std::make_unique<AVTrackPrivateAVFObjCImpl>(track);
    resetPropertiesFromTrack();
}

AVPlayerItemTrack* VideoTrackPrivateAVFObjC::playerItemTrack()
{
    return m_impl->playerItemTrack();
}

void VideoTrackPrivateAVFObjC::setAssetTrack(AVAssetTrack *track)
{
    m_impl = std::make_unique<AVTrackPrivateAVFObjCImpl>(track);
    resetPropertiesFromTrack();
}

AVAssetTrack* VideoTrackPrivateAVFObjC::assetTrack()
{
    return m_impl->assetTrack();
}

void VideoTrackPrivateAVFObjC::setSelected(bool enabled)
{
    VideoTrackPrivateAVF::setSelected(enabled);
    m_impl->setEnabled(enabled);
}
    
}

#endif

