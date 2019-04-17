package spring.data.bug

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.data.repository.Repository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.function.Function

@Component
class ValidateUniqueAccountNameFunction(
    private val accountRepository: AccountRepository
) : Function<Flux<String>, Flux<String>> {
    override fun apply(flux: Flux<String>): Flux<String> = flux
}

interface AccountRepository : Repository<AccountNameCheck, String> {
    fun existsByAccountName(accountName: String): Mono<Boolean>

    fun existsByAccountId(accountId: String): Mono<Boolean>

    fun save(accountNameCheck: AccountNameCheck): Mono<AccountNameCheck>
}

@Document
data class AccountNameCheck(
    @Indexed
    val accountId: AccountId,
    @Indexed
    val accountName: String,
    val id: String
)

inline class AccountId(val id: String)

@EnableReactiveMongoRepositories
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}