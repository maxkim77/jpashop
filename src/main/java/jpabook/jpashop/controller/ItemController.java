package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @GetMapping(value="/items/new")
    public String createForm(Model model){
        /*createForm 메서드는 Spring MVC에서 새로운 도서를 등록하기 위한
         폼을 표시하는 역할을 합니다. 이 메서드는 HTTP GET 요청을 처리하며,
         사용자가 도서 등록 페이지에 접근할 때 호출됩니다. 폼을 보여주기 위해 필요한 데이터를 준비하고,
         그 데이터를 뷰(View)에 전달하는 역할을 합니다.*/
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }
    @PostMapping(value="/items/new")
    public String create(BookForm form) {
        /*create 메서드는 Spring MVC 컨트롤러에서 **도서(Book)**를 생성하고,
        이를 데이터베이스에 저장하는 기능을 수행하는 메서드입니다.
        주로 웹 애플리케이션에서 새로운 도서를 등록할 때 사용됩니다.
        이 메서드는 HTTP POST 요청을 처리하며, 사용자가 제출한 폼 데이터를 받아서
        새로운 도서 객체를 생성하고 이를 저장하는 역할*/
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/";
    }
    @GetMapping(value="/items")
    public String list(Model model) {
        /*list 메서드는 Spring MVC 컨트롤러에서 아이템(Item) 목록을 조회하고
         이를 뷰(View)에 전달하는 역할을 합니다.
         사용자가 아이템 목록을 보기 위해 특정 URL에 접근할 때 호출되며,
         데이터베이스에서 아이템 정보를 가져와 화면에 표시하는 기능을 수행*/
        List<Item> items = itemService.findItems();
        model.addAttribute("items", itemService.findItems());
        return "items/itemList";
    }
    /**
     * 상품 수정 폼 */
    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm();

        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    /**/
    @PostMapping(value="/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form){
        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }

}