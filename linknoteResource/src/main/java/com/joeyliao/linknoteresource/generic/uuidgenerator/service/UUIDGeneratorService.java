package com.joeyliao.linknoteresource.generic.uuidgenerator.service;

import com.joeyliao.linknoteresource.generic.enums.Target;
import java.util.UUID;

public interface UUIDGeneratorService {
    UUID generateUUID(Target target);
}
