# به نام خداوند جان و خرد
#### اصول طراحی شی‌گرا سیستم‌ها | تمرین ۲
تحلیل، بازطراحی و بازآرایی کد اولیه سیستم مدیریت درخواست‌های پشتیبانی با استفاده از الگوهای طراحی و اصول شی‌گرایی جهت ساده‌تر شدن نگهداری، توسعه و فهم آن.

---

## 👥 اعضای گروه

- سید علی عترتی - [@SAEtrati](https://github.com/SAEtrati)
- سید محمد عترتی - [@SMEtrati](https://github.com/SMEtrati)

---

## 🗂️ ساختار کلی مخزن

- فاقد اصول [`Source-Ticketing-System/`](./Source-Ticketing-System)

  نسخه‌ی اصلی و اولیه کد سیستم مدیریت درخواست‌های پشتیبانی که از نظر طراحی نرم‌افزاری **کیفیت مطلوبی** ندارد.

- مطابق اصول [`Enhanced-Ticketing-System/`](./Enhanced-Ticketing-System)

  نسخه‌ی بازطراحی‌شده سیستم با اعمال الگوی مناسب طراحی و اصول شی‌گرایی.

- توضیحات `README.md`

  گزارش متنی تمرین برای همه‌ی گام‌های تمرین (همین فایل).

---

## 1️⃣ بخش اول: تشخیص زیرمسائل و الگوها

در بخش اول، هدف این است که با تحلیل کارکردهای سیستم، زیرمسائل آن را تشخیص دهیم و طبق الگوهای طراحی (که راه‌حل‌های اثبات شده هستند)، آن‌ها را بازآرایی کنیم.

> [!IMPORTANT]
> در این قسمت بیشترین سعی و تلاش بر این بوده است که طراحی کامل سیستم (و تعریف زیرمسئله‌ها) با اضافه کردن بخش‌های اساسی و مورد نیاز، به واقعیت نزدیک شود.

---

### ۱- دریافت درخواست از کانال‌های مختلف ورودی

#### ۱-۱- شرح مسئله

سیستم باید بتواند درخواست‌ها را از کانال‌های مختلف مانند ایمیل، فرم وب یا سایر کانال‌های ورودی دریافت کند. در طراحی اولیه، منطق پردازش ورودی‌ها به‌صورت مستقیم بود و در سرویس اصلی قرار داشت که توسعه سیستم را دشوار می‌کرد.

#### ۲-۱- الگوی پیشنهادی

الگوی استراتژی (Strategy)
- برای کپسوله کردن منطق پردازش هر کانال ورودی.

#### ۳-۱- روش اعمال و پیاده‌سازی

- اینترفیس `InputHandlerStrategy` تعریف شد که متد `handle(rawData)` را مشخص می‌کند.
- دو پیاده‌سازی مستقل `EmailInputHandler` و `WebInputHandler` ایجاد شدند که هرکدام مسئول پردازش داده ورودی خود هستند.
- در سرویس اصلی، به جای شرط و سوییچ، Strategy مناسب تزریق و استفاده می‌شود.

---

### ۲- تبدیل ورودی‌ها به آبجکت (ایجاد درخواست)

#### ۱-۲- شرح مسئله

داده‌های خام ورودی باید به یک شیء استاندارد تبدیل شوند تا در کل سیستم مورد استفاده قرار گیرند. در نسخه اولیه، ساخت آبجکت‌ها پراکنده و غیرمتمرکز بود.

#### ۲-۲- الگوی پیشنهادی

الگوی کارخانه Factory
- برای ایجاد متمرکز و قابل توسعه شیء درخواست (Ticket)

#### ۳-۲- روش اعمال و پیاده‌سازی

- کلاس `TicketFactory` با متد `create(data, channel)` طراحی شد.
- این کلاس مسئولیت ساخت و مقداردهی اولیه تمام تیکت‌های سیستم را بر عهده گرفت و منطق ایجاد شیء را در یک نقطه متمرکز کرد.

---

### ۳- تعیین واحد مسئول و رسیدگی بر اساس نوع درخواست (فنی/مالی/عمومی)

#### ۱-۳- شرح مسئله

پس از ثبت درخواست، سیستم باید تصمیم بگیرد که درخواست به کدام واحد (مثلا تیم فنی یا مالی) ارجاع شود. در نسخه اولیه، این منطق به‌صورت شرطی و سخت پیاده‌سازی شده بود.

#### ۲-۳- الگوی پیشنهادی

الگوی استراتژی Strategy
- تعیین سیاست تخصیص درخواست‌ها

#### ۳-۳- روش اعمال و پیاده‌سازی

- اینترفیس `AssignStrategy` تعریف شد که متد `assign(Ticket t)` را مشخص می‌کند.
- پیاده‌سازی `DefaultAssignStrategy` بر اساس نوع `Ticket (INCIDENT, REQUEST, QUESTION)` واحد مناسب زا تعیین می‌کند.
- امکان افزودن استراتژی‌های پیچیده‌تر (مثلا بر اساس بار کاری یا اولویت) بدون تغییر در کدهای موجود فراهم شده است.

---

### ۴- پاسخ‌دهی و ارسال بر اساس کانال

#### ۱-۴- شرح مسئله

پس از ثبت درخواست، باید پیام تایید از طریق کانال مناسب (ایمیل، SMS یا پورتال) ارسال شود. طراحی اولیه وابسته به ایمیل بود و توسعه کانال جدید نیازمند تغییر در چندین کلاس بود.

#### ۲-۴- الگوی پیشنهادی

الگوی استراتژی Strategy
- برای ارسال پاسخ از کانال‌های مختلف

#### ۳-۴- روش اعمال و پیاده‌سازی

- اینترفیس `ResponseStrategy` با متد `send(ticket, message)` طراحی شد.
- پیاده‌سازی‌های `EmailResponseStrategy` و `SmsResponseStrategy` اضافه شدند.
- `TicketingFacade` استراتژی پاسخ‌دهی مناسب را برای ارسال پیام به کار می‌گیرد.

---

### ۵- وضعیت‌های درخواست در طول چرخه حیات (ایجاد/ارجاع/در حال رسیدگی/حل‌شده/بسته)

#### ۱-۵- شرح مسئله

هر درخواست در طول عمر خود وضعیت‌های مختلفی را طی می‌کند و قوانین انتقال وضعیت باید به‌صورت ساخت‌یافته مدیریت شوند.

#### ۲-۵- الگوی پیشنهادی

الگوی وضعیت State
- برای مدیریت هر مرحله از چرخه حیات درخواست‌ها

#### ۳-۵- روش اعمال و پیاده‌سازی

- اینترفیس `TicketState` تعریف شد و هر وضعیت (`NewState`, `AssignedState`, `InProgressState`, `ResolvedState`, `ClosedState`) به یک کلاس مجزا تبدیل شد.
- هر کلاس وضعیت، منطق مربوط به خود و گذارهای مجاز به وضعیت بعدی را مدیریت می‌کند.
- کلاس `Ticket` تنها وضعیت جاری خود را نگه می‌دارد و عملیات تغییر وضعیت را به کلاس وضعیت فعلی واگذار می‌کند.

---

### ۶- ثبت رویداد (لاگ)

#### ۱-۶- شرح مسئله

برای پایش سیستم، نیاز به ثبت رویدادها (دریافت درخواست، تغییر وضعیت، ارسال پیام و ...) در جریان فرآیند‌ها وجود دارد.

#### ۲-۶- الگوی پیشنهادی

صرفا یک لاگر ساده


#### ۳-۶- روش اعمال و پیاده‌سازی

- `Logger` و `ConsoleLogger` + رکورد `Event`: تا پس از هر مرحله لاگ نوشته شود.

---

### ۷- منابع مشترک (اتصال به پایگاه‌داده‌ها):

#### ۱-۷- شرح مسئله

سیستم به یک منبع مشترک برای ذخیره اطلاعات (تیکت‌ها، لاگ‌ها) نیاز دارد و ایجاد چندین نمونه از اتصال پایگاه‌داده می‌تواند منجر به ناسازگاری شود.

#### ۲-۷- الگوی پیشنهادی

الگوی Singleton
- برای تضمین وجود تنها یک نمونه از اتصال پایگاه داده.

#### ۳-۷- روش اعمال و پیاده‌سازی

- کلاس `DatabaseConnection` به صورت `Singleton` پیاده‌سازی شد.

---

### ۸- ساده‌سازی API بیرونی برای استفاده متداول

#### ۱-۸- شرح مسئله

تعامل با سرویس‌های بیرونی مانند ایمیل و پیامک و همچنین زیرسیستم‌های مختلف (پردازش ورودی، ساخت تیکت، تخصیص، اطلاع‌رسانی) پیچیده است و بهتر است یک لایه ساده برای استفاده در سیستم فراهم شود.

#### ۲-۸- الگوی پیشنهادی

الگوی Facade
- برای ساده‌سازی دسترسی به رابط/واسط‌های خارجی (API)

#### ۳-۸- روش اعمال و پیاده‌سازی

- کلاس `ExternalApiFacade` ایجاد شد که متدهای `sendEmail` و `sendSms` را ارائه می‌دهد.
- کلاس `TicketingFacade` سناریوهای اصلی سیستم (ثبت و پیشبرد درخواست) را ساده‌سازی می‌کند.

---
## 2️⃣ بخش دوم: ترسیم نمودار کلاس

نمودار زیر، ساختار بازطراحی‌شده سیستم و محیط را با تمرکز بر الگوهای کلیدی اعمال‌شده نمایش می‌دهد.

```mermaid
classDiagram
%% ---------- Enumerations ----------
class Channel {
  +EMAIL
  +WEB
  +SMS
  +PORTAL
}
class TicketType {
  +INCIDENT
  +REQUEST
  +QUESTION
}
class Priority {
  +LOW
  +MEDIUM
  +HIGH
  +CRITICAL
}

%% ---------- Domain classes (blue) ----------
class Ticket {
  +id : String
  +title : String
  +description : String
  +type : TicketType
  +priority : Priority
  +createdAt : DateTime
  +updatedAt : DateTime
  +assignedTo : Agent
  +department : Department
  +state : TicketState
  +comments : List<Comment>
  +attachments : List<Attachment>
  +metadata : Map<String,String>
  +applyAction(action : String)
  +changeState(next : TicketState)
  +addComment(c : Comment)
  +assignTo(a : Agent)
  +getSummary() : String
}

class Customer {
  +id : String
  +name : String
  +email : String
  +phone : String
}
class Agent {
  +id : String
  +name : String
  +email : String
  +dept : Department
  +ticketsAssigned : List<Ticket>
  +acceptTicket(t : Ticket)
  +resolveTicket(t : Ticket)
}
class Department {
  +id : String
  +name : String
  +agents : List<Agent>
  +addAgent(a : Agent)
}
class Comment {
  +id : String
  +text : String
  +createdAt : DateTime
  +author : Customer
}
class Attachment {
  +id : String
  +filename : String
  +url : String
  +size : long
}
class EventLog {
  +id : String
  +eventType : String
  +details : String
  +timestamp : DateTime
}

%% ---------- Factories / Repositories / Infra (green) ----------
class TicketFactory {
  +create(data : Map<String,String>, channel : Channel, customer : Customer) : Ticket
  +createFromEmail(raw : String) : Ticket
}
class TicketRepository {
  +save(t : Ticket)
  +findById(id : String) : Ticket
  +findByFilter(f : Map) : List<Ticket>
  +update(t : Ticket)
  +delete(id : String)
}
class DatabaseConnection {
  -instance : DatabaseConnection
  -connectionPool : Object
  +getInstance() : DatabaseConnection
  +getConnection() : Object
}
class ExternalApiFacade {
  +sendEmail(to : String, subject : String, body : String)
  +sendSms(to : String, body : String)
  +pushPortalNotification(userId : String, body : String)
}
class TicketingFacade {
  +submit(rawData : String, channel : Channel) : Ticket
  +process(rawData : String, channel : Channel) : Ticket
  +assignAndNotify(ticket : Ticket)
  +transition(ticketId : String, action : String)
}

%% ---------- Logger ----------
class Logger {
  <<interface>>
  +log(level : String, message : String)
}
class ConsoleLogger {
  +log(level : String, message : String)
}

%% ---------- Strategies / Patterns ----------
class InputHandlerStrategy {
  <<interface>>
  +handle(rawData : String) : Map<String,String>
}
class EmailInputHandler {
  +handle(rawData : String) : Map<String,String>
}
class WebInputHandler {
  +handle(rawData : String) : Map<String,String>
}
class AssignStrategy {
  <<interface>>
  +assign(t : Ticket) : Department
}
class DefaultAssignStrategy {
  +assign(t : Ticket) : Department
}
class ResponseStrategy {
  <<interface>>
  +send(t : Ticket, message : String)
}
class EmailResponseStrategy {
  +send(t : Ticket, message : String)
}
class SmsResponseStrategy {
  +send(t : Ticket, message : String)
}
class PortalResponseStrategy {
  +send(t : Ticket, message : String)
}

%% ---------- State pattern (ساده و خوانا) ----------
class TicketState {
  <<interface>>
  +name : String
  +enter(t : Ticket)
  +exit(t : Ticket)
  +next(action : String) : TicketState
}
class NewState {
  +enter(t : Ticket)
  +next(action : String) : TicketState
}
class AssignedState {
  +enter(t : Ticket)
  +next(action : String) : TicketState
}
class InProgressState {
  +enter(t : Ticket)
  +next(action : String) : TicketState
}
class ResolvedState {
  +enter(t : Ticket)
  +next(action : String) : TicketState
}
class ClosedState {
  +enter(t : Ticket)
  +next(action : String) : TicketState
}

%% ---------- Relationships ----------
Ticket "1" o-- "1" Customer : createdBy
Agent "1" o-- "1" Department : memberOf
Department "1" o-- "*" Agent : employs
Ticket "1" *-- "1" TicketState : currentState
Ticket "1" o-- "*" Comment : has
Ticket "1" o-- "*" Attachment : has
TicketRepository ..> DatabaseConnection : uses
TicketRepository "1" -- "*" Ticket : persists
TicketFactory ..> Ticket : creates
TicketingFacade ..> InputHandlerStrategy : uses
TicketingFacade ..> TicketFactory : uses
TicketingFacade ..> AssignStrategy : uses
TicketingFacade ..> ResponseStrategy : uses
TicketingFacade ..> TicketRepository : uses
TicketingFacade ..> ExternalApiFacade : uses
TicketingFacade ..> Logger : logs
Logger <|.. ConsoleLogger
InputHandlerStrategy <|.. EmailInputHandler
InputHandlerStrategy <|.. WebInputHandler
AssignStrategy <|.. DefaultAssignStrategy
ResponseStrategy <|.. EmailResponseStrategy
ResponseStrategy <|.. SmsResponseStrategy
ResponseStrategy <|.. PortalResponseStrategy
TicketState <|.. NewState
TicketState <|.. AssignedState
TicketState <|.. InProgressState
TicketState <|.. ResolvedState
TicketState <|.. ClosedState

```

---

## 3️⃣ بخش سوم: اعمال الگوها

در این گام، مطابق صورت تمرین، یک نسخه بازطراحی‌شده از سیستم در پوشه [`Enhanced-Ticketing-System/`](./Enhanced-Ticketing-System) پیاده‌سازی شد. تمرکز اصلی بر اصلاح نقض اصول طراحی شی‌گرا و الگوها بود.

۱- **تمرکز بیش از حد مسئولیت‌ها در کلاس**

۲- **طراحی نامناسب بخش تغییر وضعیت‌ها** (استفاده از شرط‌های تودرتو)  

۳- **وابستگی مستقیم به کانال‌های ورودی و خروجی** (نقض‌های OCP و DIP)

کلیات نحوه پیاده‌سازی برای هر یک از زیرمسائل با بیان الگوی مناسب آن در بخش اول بیان شد. جزئیات پیاده‌سازی الگوها در سورس کد قابل مشاهده است. در نسخه بازطراحی‌شده، هر الگو به تفکیک در پکیج‌های مجزا پیاده‌سازی شده است تا ساختار کد خواناتر و مدیریت آن ساده‌تر باشد.

---

## 4️⃣ بخش چهارم: تحلیل شی‌گرا

در این گام، نسخه اولیه سیستم از نظر اصول شی‌گرایی بررسی و با نسخه بازطراحی‌شده مقایسه شده است.

### ۰- اصول مورد بررسی

- **SRP** – Single Responsibility Principle  
- **OCP** – Open/Closed Principle  
- **LSP** – Liskov Substitution Principle  
- **ISP** – Interface Segregation Principle  
- **DIP** – Dependency Inversion Principle  
- **PLK** – Principle of Least Knowledge (Law of Demeter)  
- **CRP** – Common Reuse Principle  

---

### ۱- تحلیل SRP – اصل تک مسئولیتی

---

### ۲- تحلیل OCP – اصل باز/بسته بودن

---

### ۳- تحلیل LSP – اصل جایگزینی لیسکوف

---

### ۴- تحلیل ISP – اصل تفکیک اینترفیس

---

### ۵- تحلیل DIP – اصل وارونگی وابستگی

---

### ۶- تحلیل PLK – قانون کمترین دانش (Law of Demeter)

---

### ۷- تحلیل CRP – اصل استفاده‌ی مشترک (Common Reuse Principle)

---

## 5️⃣ بخش آخر: نتیجه‌گیری (اضافی)
