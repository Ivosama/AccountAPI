package com.idts.accountapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Entity
@Data
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "accountName")
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Setter
    @Size(min = 3, max = 12, message = "Account name must be between 3 and 12 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+", message = "Account name must contain only letters and/or numbers")
    @Column(unique = true)
    private String accountName;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)", insertable = false)
    @ColumnDefault("0.0")
    private Double balance;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    public void setAccountName(String accountName) {
        this.accountName = this.getUser().getId() + "-" + accountName;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Account account))
            return false;
        return Objects.equals(this.id, account.id) && Objects.equals(this.accountName, account.accountName) &&
                Objects.equals(this.user, account.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.accountName, this.user);
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + this.id +
                ", accountName='" + this.accountName + '\'' +
                ", user='" + this.user + '\'' + '}';
    }
}