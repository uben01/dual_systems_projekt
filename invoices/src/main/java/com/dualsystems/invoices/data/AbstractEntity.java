package com.dualsystems.invoices.data;

import org.apache.commons.codec.binary.Base64;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.nio.ByteBuffer;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Column(name = "ID")
    protected String id;

    public AbstractEntity(String id) {
        this.id = id;
    }

    public AbstractEntity() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        this.id = Base64.encodeBase64URLSafeString(bb.array());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractEntity)) {
            return false;
        }
        return ((AbstractEntity) o).getId().equals(getId());
    }
}
