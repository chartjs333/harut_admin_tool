package org.medical.hub.request;

import lombok.Getter;
import lombok.Setter;
import org.medical.hub.models.FileType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadRequest {

    MultipartFile file;

    FileType fileType;
}
